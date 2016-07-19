package rocks.bastion.core.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.entity.ContentType;
import rocks.bastion.core.Response;

import java.io.IOException;
import java.util.Optional;

public class JsonResponseDecoder implements ResponseDecoder {

    private static ObjectMapper jsonObjectMapper = null;

    @Override
    public Optional<?> decode(Response response, DecodingHints hints) {
        ContentType responseContentType = response.getContentType().orElse(ContentType.DEFAULT_TEXT);
        if (!supportsContentType(responseContentType)) {
            return Optional.empty();
        }
        JsonNode decodedJsonTree;
        try {
            decodedJsonTree = getObjectMapper().readTree(response.getBody());
        } catch (IOException e) {
            return Optional.empty();
        }
        Optional<?> decodedModel = decodeTreeUsingHints(decodedJsonTree, hints);
        return decodedModel;
    }

    private synchronized static ObjectMapper getObjectMapper() {
        if (jsonObjectMapper == null) {
            jsonObjectMapper = new ObjectMapper();
        }
        return jsonObjectMapper;
    }

    private Optional<?> decodeTreeUsingHints(JsonNode decodedJsonTree, DecodingHints hints) {
        return Optional.of(hints.getModelType().<Object>map(modelType -> {
            try {
                return getObjectMapper().treeToValue(decodedJsonTree, modelType);
            } catch (JsonProcessingException ignored) {
                return null;
            }
        }).orElse(decodedJsonTree));
    }

    private boolean supportsContentType(ContentType responseContentType) {
        return responseContentType.getMimeType().equals(ContentType.APPLICATION_JSON.getMimeType());
    }
}
