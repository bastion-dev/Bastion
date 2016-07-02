package rocks.bastion.core.assertions;

import org.junit.Assert;
import org.junit.Test;
import rocks.bastion.core.ModelResponse;
import rocks.bastion.core.RawResponse;

import java.io.ByteArrayInputStream;
import java.util.Collections;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class JsonResponseAssertionsTest {
    @Test
    public void execute() throws Exception {
        try {
            JsonResponseAssertions assertions = JsonResponseAssertions.fromString(200, "{ \"key\":\"kyle\", \"surname\":\"pullicino\" }");
            assertions.execute(200, new ModelResponse<>(new RawResponse(200, "OK", Collections.emptyList(), new ByteArrayInputStream("{ \"key\":\"kyle1\", \"surname\":\"pullicino\", \"array\":[1, 2] }".getBytes())),
                    "{ \"key\":\"kyle\", \"surname\":\"pullicino\" }"), "{ \"key\":\"kyle\", \"surname\":\"pullicino\" }");
        } catch (AssertionError assertionError) {
            Assert.assertEquals("Assertion Failed Message", assertionError.getMessage(), "Actual response body is not as expected. The following JSON Patch (as per RFC-6902) tells you what operations you need to perform to transform the actual response body into the expected response body:" +
                    "\n" +
                    " [{\"op\":\"replace\",\"path\":\"/key\",\"value\":\"kyle\"},{\"op\":\"remove\",\"path\":\"/array\"}]");
            return;
        }
        Assert.fail("An assertion error should have been thrown by the JSON Response Assertions");
    }
}