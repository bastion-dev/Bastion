package rocks.bastion.core.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.entity.ContentType;
import rocks.bastion.core.Response;

import java.io.IOException;
import java.util.Optional;

/**
 * A {@link ResponseDecoder} which will interpret an HTTP response containing JSON content body. This implementation uses
 * the Jackson library's {@link ObjectMapper} to perform the decoding operation.
 * <p>
 * The decoder uses the following strategy when attempting to construct the model object for the response: first, it
 * parses the HTTP response's JSON content into an {@link JsonNode abstract syntax tree representing the given JSON}
 * (known as a JSON tree). Then, if the user has supplied a target model type, it binds the JSON tree into an instance
 * of that type. Otherwise, it immediately returns the decoded JSON tree as an object of type {@link JsonNode}.
 */
public class JsonResponseDecoder implements ResponseDecoder {

    private static ObjectMapper jsonObjectMapper = null;

    @Override
    public Optional<?> decode(Response response, DecodingHints hints) {
        ContentType responseContentType = response.getContentType().orElse(ContentType.DEFAULT_TEXT);
        if (!isSupportsContentType(responseContentType)) {
            return Optional.empty();
        }
        JsonNode decodedJsonTree;
        try {
            decodedJsonTree = getObjectMapper().readTree(response.getBody());
        } catch (JsonProcessingException ignored) {
            return Optional.empty();
        } catch (IOException exception) {
            throw new IllegalStateException("An unexpected error occurred while reading JSON data", exception);
        }
        return decodeTreeUsingHints(decodedJsonTree, hints);
    }

    private static synchronized ObjectMapper getObjectMapper() {
        if (jsonObjectMapper == null) {
            jsonObjectMapper = new ObjectMapper();
        }
        return jsonObjectMapper;
    }

    private Optional<?> decodeTreeUsingHints(TreeNode decodedJsonTree, DecodingHints hints) {
        return Optional.of(hints.getModelType().<Object>map(modelType -> {
            try {
                return getObjectMapper().treeToValue(decodedJsonTree, modelType);
            } catch (JsonProcessingException ignored) {
                return null;
            }
        }).orElse(decodedJsonTree));
    }

    private boolean isSupportsContentType(ContentType responseContentType) {
        return responseContentType.getMimeType().equals(ContentType.APPLICATION_JSON.getMimeType());
    }
}
