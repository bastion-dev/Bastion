package rocks.bastion.core.assertions;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import com.google.common.io.Files;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.apache.http.entity.ContentType;
import org.junit.Assert;
import rocks.bastion.core.ModelResponse;
import rocks.bastion.core.Response;
import rocks.bastion.core.request.InvalidJsonException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

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

    private JsonResponseAssertions(int expectedStatusCode, String expectedJson) {
        Objects.requireNonNull(expectedJson);

        this.expectedStatusCode = expectedStatusCode;
        this.contentType = ContentType.APPLICATION_JSON;
        this.expectedJson = expectedJson;

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

    private void validateExpectedJson() throws InvalidJsonException {
        try {
            new JsonParser().parse(expectedJson);
        } catch (JsonParseException parseException) {
            throw new InvalidJsonException(parseException, this.expectedJson);
        }
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

    private void assertContentTypeHeader(ModelResponse<?> response) {
        Assert.assertTrue("Content-type exists in response", response.getContentType().isPresent());
        Assert.assertEquals("Content-type MIME type", contentType.getMimeType(), response.getContentType().get().getMimeType());
    }

    private JsonNode computeJsonPatch(Response response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonNode actualJsonTree = factory.createParser(response.getBody()).readValueAsTree();
        JsonNode expectedJsonTree = factory.createParser(expectedJson).readValueAsTree();
        return JsonDiff.asJson(actualJsonTree, expectedJsonTree);
    }

    /**
     * The assertions object will initially check that the content-type header returned by the actual response is
     * "application/json". This can be overriden to check for a different content-type header using this method. Despite
     * this, this assertions object will still try to interpret the body as if it were JSON text.
     *
     * @param contentType The expected content-type header
     */
    public void overrideContentType(ContentType contentType) {
        Objects.requireNonNull(contentType);
        this.contentType = contentType;
    }

}
