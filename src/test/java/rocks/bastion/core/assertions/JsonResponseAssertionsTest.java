package rocks.bastion.core.assertions;

import org.junit.Assert;
import org.junit.Test;
import rocks.bastion.core.ModelResponse;
import rocks.bastion.core.RawResponse;
import rocks.bastion.core.request.InvalidJsonException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Collections;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class JsonResponseAssertionsTest {

    @Test(expected = InvalidJsonException.class)
    public void fromString_invalidJson_shouldThrowException() throws Exception {
        JsonResponseAssertions.fromString(200, "{ \"key\":\"kyle\", \"surname\":\"pullicino\" ");
    }

    @Test
    public void execute_fromStringJsonMismatches_shouldThrowErrorWithDiff() throws Exception {
        try {
            JsonResponseAssertions assertions = JsonResponseAssertions.fromString(200, "{ \"key\":\"kyle\", \"surname\":\"pullicino\" }");
            ModelResponse<String> response = prepareModelResponse("{ \"key\":\"kyle1\", \"surname\":\"pullicino\", \"array\":[1, 2] }");
            assertions.execute(200, response, response.getModel());
        } catch (AssertionError assertionError) {
            Assert.assertEquals("Assertion Failed Message", assertionError.getMessage(), "Actual response body is not as expected. The following JSON Patch (as per RFC-6902) tells you what operations you need to perform to transform the actual response body into the expected response body:" +
                    "\n" +
                    " [{\"op\":\"replace\",\"path\":\"/key\",\"value\":\"kyle\"},{\"op\":\"remove\",\"path\":\"/array\"}]");
            return;
        }
        Assert.fail("An assertion error should have been thrown by the JSON Response Assertions");
    }

    @Test
    public void execute_fromFileJsonMismatches_shouldThrowErrorWithDiff() throws Exception {
        try {
            JsonResponseAssertions assertions = JsonResponseAssertions.fromFile(200, new File(JsonResponseAssertions.class.getResource("/rocks/bastion/core/assertions/test-body.json").toURI()));
            ModelResponse<String> response = prepareModelResponse("{\n" +
                    "  \"name\": \"john\",\n" +
                    "  \"timestamp1\": \"2016-10-15T20:00:25+0100\",\n" +
                    "  \"colours\": [\"blue\"],\n" +
                    "  \"favourites\": {\n" +
                    "    \"food\": \"apples\",\n" +
                    "    \"number\": 23,\n" +
                    "    \"country\": \"Spain\"" +
                    "  }\n" +
                    "}");
            assertions.execute(200, response, response.getModel());
        } catch (AssertionError assertionError) {
            Assert.assertEquals("Assertion Failed Message", assertionError.getMessage(), "Actual response body is not as expected. The following JSON Patch (as per RFC-6902) tells you what operations you need to perform to transform the actual response body into the expected response body:\n" +
                    " [{\"op\":\"move\",\"path\":\"/timestamp\",\"from\":\"/timestamp1\"},{\"op\":\"remove\",\"path\":\"/colours\"},{\"op\":\"remove\",\"path\":\"/favourites/country\"},{\"op\":\"add\",\"path\":\"/favourites/colours\",\"value\":[\"blue\",\"red\"]}]");
            return;
        }
        Assert.fail("An assertion error should have been thrown by the JSON Response Assertions");
    }

    private ModelResponse<String> prepareModelResponse(String jsonContent) {
        return new ModelResponse<>(new RawResponse(200, "OK", Collections.emptyList(), new ByteArrayInputStream(jsonContent.getBytes())),
                jsonContent);
    }
}