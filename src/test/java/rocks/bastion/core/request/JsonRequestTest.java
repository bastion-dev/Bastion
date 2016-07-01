package rocks.bastion.core.request;

import org.apache.http.entity.ContentType;
import org.junit.Test;
import rocks.bastion.external.HttpMethod;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class JsonRequestTest {

    @Test
    public void fromString_validJson_shouldReturnARequest() throws Exception {
        JsonRequest request = JsonRequest.fromString(HttpMethod.POST, "http://test.test", "{ \"key\":\"value\", \"array\":[ \"1\", \"test\" ] }");
        assertThat(request.name()).describedAs("Request Name").isEqualTo("POST http://test.test");
        assertThat(request.url()).describedAs("Request URL").isEqualTo("http://test.test");
        assertThat(request.method()).describedAs("Request Method").isEqualTo(HttpMethod.POST);
        assertThat(request.contentType()).describedAs("Request Content-type").isEqualTo(ContentType.APPLICATION_JSON);
        assertThat(request.headers()).describedAs("Request Headers").isEmpty();
        assertThat(request.queryParams()).describedAs("Request Query Parameters").isEmpty();
        assertThat(request.body()).describedAs("Request Body").isEqualTo("{ \"key\":\"value\", \"array\":[ \"1\", \"test\" ] }");
    }

    @Test
    public void postFromString() throws Exception {
        // TODO: Test
    }

    @Test
    public void putFromString() throws Exception {
        // TODO: Test
    }

    @Test
    public void fromFile() throws Exception {
        // TODO: Test
    }

    @Test
    public void postFromFile() throws Exception {
        // TODO: Test
    }

    @Test
    public void putFromFile() throws Exception {
        // TODO: Test
    }

    @Test
    public void overrideContentType() throws Exception {
        // TODO: Test
    }

    @Test
    public void addHeader() throws Exception {
        // TODO: Test
    }

    @Test
    public void addQueryParam() throws Exception {
        // TODO: Test
    }

}