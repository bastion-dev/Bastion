package rocks.bastion.core.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import com.google.common.io.Files;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.apache.http.entity.ContentType;
import org.junit.Assert;
import rocks.bastion.core.Assertions;
import rocks.bastion.core.ModelResponse;
import rocks.bastion.core.Response;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import static java.lang.String.format;

/**
 * Performs assertions on a response by checking for status code and content-type. The user supplies a JSON string
 * that will act as the expected body of the response. Bastion will then compare the actual received JSON string
 * with the user-supplied expectation. The comparison will not be a straight-up string equality check though: Bastion
 * will interpret the JSON of both the expected body and the actual response body and perform a structural comparison. This
 * is important, because the JSON specification says that a JSON object is unordered and we do not want a different
 * order of properties to fail the assertion.
 *
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class JsonResponseAssertions implements Assertions<Object> {

    private int expectedStatusCode;
    private ContentType contentType;
    private String expectedJson;
    private Collection<String> ignoredFieldsValue;

    private JsonResponseAssertions(int expectedStatusCode, String expectedJson) {
        Objects.requireNonNull(expectedJson);

        this.expectedStatusCode = expectedStatusCode;
        this.contentType = ContentType.APPLICATION_JSON;
        this.expectedJson = expectedJson;
        this.ignoredFieldsValue = new HashSet<>();

        validateExpectedJson();
    }

    public static JsonResponseAssertions fromString(int expectedStatusCode, String expectedJson) {
        return new JsonResponseAssertions(expectedStatusCode, expectedJson);
    }

    public static JsonResponseAssertions fromFile(int expectedStatusCode, File expectedJsonFile) {
        try {
            Objects.requireNonNull(expectedJsonFile);
            return new JsonResponseAssertions(expectedStatusCode, Files.asCharSource(expectedJsonFile, Charset.defaultCharset()).read());
        } catch (IOException e) {
            throw new RuntimeException(format("An error occurred while reading a file %s", expectedJsonFile), e);
        }
    }

    private static void assertJsonPatchIsEmpty(JsonNode jsonPatch) {
        if (jsonPatch.size() != 0) {
            Assert.fail(format("Actual response body is not as expected. The following JSON Patch (as per RFC-6902) tells you what operations you need " +
                    "to perform to transform the actual response body into the expected response body:\n %s", jsonPatch.toString()));
        }
    }

    /**
     * Ignore particular fields' values in the actual JSON response. Notice that Bastion will still fail the assertion if a
     * JSON field is missing, is in the wrong place, or is extra. Ignoring fields' values using this method is useful for
     * randomly generated values in the response, such as IDs or timestamps.
     * <br><br>
     * Implementation wise, when performing the JSON patch diff between the expected and the actual responses, Bastion will ignore
     * any patch operations which have {@code op} {@code "replace"} and a field which is one of the ignored fields.
     *
     * @param fields The fields' names to ignore
     * @return This object (for method chaining)
     */
    public JsonResponseAssertions ignoreFieldsValues(String... fields) {
        Objects.requireNonNull(fields);
        Arrays.stream(fields).forEach(this::ignoreSingleFieldValue);
        return this;
    }

    private void ignoreSingleFieldValue(String field) {
        Objects.requireNonNull(field);
        ignoredFieldsValue.add(field);
    }

    /**
     * The assertions object will initially check that the content-type header returned by the actual response is
     * "application/json". This can be overriden to check for a different content-type header using this method. Despite
     * this, this assertions object will still try to interpret the body as if it were JSON text.
     *
     * @param contentType The expected content-type header
     * @return This object (for method chaining)
     */
    public JsonResponseAssertions overrideContentType(ContentType contentType) {
        Objects.requireNonNull(contentType);
        this.contentType = contentType;
        return this;
    }

    @Override
    public void execute(int statusCode, ModelResponse<?> response, Object model) throws AssertionError {
        try {
            Assert.assertEquals("Response Status Code", expectedStatusCode, statusCode);
            assertContentTypeHeader(response);
            JsonNode jsonPatch = computeJsonPatch(response);
            assertJsonPatchIsEmpty(jsonPatch);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while parsing JSON text", e);
        }
    }

    private void validateExpectedJson() throws InvalidJsonException {
        try {
            new JsonParser().parse(expectedJson);
        } catch (JsonParseException parseException) {
            throw new InvalidJsonException(parseException, this.expectedJson);
        }
    }

    private void assertContentTypeHeader(ModelResponse<?> response) {
        Assert.assertTrue("Content-type exists in response", response.getContentType().isPresent());
        Assert.assertEquals("Content-type MIME type", contentType.getMimeType(), response.getContentType().get().getMimeType());
    }

    private JsonNode computeJsonPatch(Response response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonNode actualJsonTree = factory.createParser(response.getBody()).readValueAsTree();
        JsonNode expectedJsonTree = factory.createParser(expectedJson).readValueAsTree();
        JsonNode jsonPatch = JsonDiff.asJson(actualJsonTree, expectedJsonTree);
        removeReplaceOpsForIgnoredFields(jsonPatch);
        return jsonPatch;
    }

    private void removeReplaceOpsForIgnoredFields(JsonNode jsonPatch) {
        Iterator<JsonNode> patchIterator = jsonPatch.iterator();
        while (patchIterator.hasNext()) {
            JsonNode patchOperation = patchIterator.next();
            JsonNode operationType = patchOperation.get("op");
            JsonNode pathName = patchOperation.get("path");
            if (operationType.asText().equals("replace") && ignoredFieldsValue.contains(pathName.asText())) {
                patchIterator.remove();
            }
        }
    }

}
