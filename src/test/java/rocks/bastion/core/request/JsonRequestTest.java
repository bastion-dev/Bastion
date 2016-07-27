package rocks.bastion.core.request;

import org.apache.http.entity.ContentType;
import org.junit.Test;
import rocks.bastion.core.ApiHeader;
import rocks.bastion.core.ApiQueryParam;
import rocks.bastion.core.HttpMethod;
import rocks.bastion.core.json.InvalidJsonException;
import rocks.bastion.core.json.JsonRequest;
import rocks.bastion.core.resource.ResourceNotFoundException;

import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonRequestTest {

    @Test(expected = InvalidJsonException.class)
    public void fromString_invalidJson_shouldThrowException() throws Exception {
        JsonRequest request = JsonRequest.fromString(HttpMethod.POST, "http://test.test", "{ \"key\":\"value\", \"array\":[ \"1\", \"test\" }");
    }

    @Test
    public void fromString_validJson_shouldReturnARequest() throws Exception {
        JsonRequest request = JsonRequest.fromString(HttpMethod.POST, "http://test.test", "{ \"key\":\"value\", \"array\":[ \"1\", \"test\" ] }");
        assertJsonRequestAttributes(request, "POST http://test.test", HttpMethod.POST, "{ \"key\":\"value\", \"array\":[ \"1\", \"test\" ] }");
    }

    @Test
    public void postFromString_validJson_shouldReturnARequest() throws Exception {
        JsonRequest request = JsonRequest.postFromString("http://test.test", "{ \"key\":\"value\", \"array\":[ \"1\", \"test\" ] }");
        assertJsonRequestAttributes(request, "POST http://test.test", HttpMethod.POST, "{ \"key\":\"value\", \"array\":[ \"1\", \"test\" ] }");
    }

    @Test
    public void putFromString_validJson_shouldReturnARequest() throws Exception {
        JsonRequest request = JsonRequest.putFromString("http://test.test", "{ \"key\":\"value\", \"array\":[ \"1\", \"test\" ] }");
        assertJsonRequestAttributes(request, "PUT http://test.test", HttpMethod.PUT, "{ \"key\":\"value\", \"array\":[ \"1\", \"test\" ] }");
    }

    @Test
    public void fromFile_validJson_shouldReturnARequest() throws Exception {
        JsonRequest request = JsonRequest.fromFile(HttpMethod.POST, "http://test.test", getValidJsonFile());
        assertJsonRequestAttributes(request, "POST http://test.test", HttpMethod.POST, "{\n" +
                "  \"name\": \"john\",\n" +
                "  \"timestamp\": \"2016-10-15T20:00:25+0100\",\n" +
                "  \"favourites\": {\n" +
                "    \"food\": \"apples\",\n" +
                "    \"colours\": [\"blue\", \"red\"],\n" +
                "    \"number\": 23\n" +
                "  }\n" +
                "}");
    }

    @Test
    public void postFromFile_validJson_shouldReturnARequest() throws Exception {
        JsonRequest request = JsonRequest.postFromResource("http://test.test", getValidJsonFile());
        assertJsonRequestAttributes(request, "POST http://test.test", HttpMethod.POST, "{\n" +
                "  \"name\": \"john\",\n" +
                "  \"timestamp\": \"2016-10-15T20:00:25+0100\",\n" +
                "  \"favourites\": {\n" +
                "    \"food\": \"apples\",\n" +
                "    \"colours\": [\"blue\", \"red\"],\n" +
                "    \"number\": 23\n" +
                "  }\n" +
                "}");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void putFromFile_inexistantFile_shouldThrowAnException() throws Exception {
        JsonRequest request = JsonRequest.putFromResource("http://test.test", "inexistant.json");
    }

    @Test
    public void overrideContentType() throws Exception {
        JsonRequest request = JsonRequest.fromString(HttpMethod.POST, "http://test.test", "{ \"key\":\"value\", \"array\":[ \"1\", \"test\" ] }");
        request.overrideContentType(ContentType.TEXT_PLAIN);
        assertThat(request.contentType()).isEqualTo(ContentType.TEXT_PLAIN);
    }

    @Test
    public void addHeader() throws Exception {
        JsonRequest request = JsonRequest.fromString(HttpMethod.POST, "http://test.test", "{ \"key\":\"value\", \"array\":[ \"1\", \"test\" ] }");
        request.addHeader("header1", "value1");
        request.addHeader("header2", "value2");
        assertThat(request.headers()).hasSize(2);
        assertThat(request.headers()).containsExactly(new ApiHeader("header1", "value1"), new ApiHeader("header2", "value2"));
    }

    @Test
    public void addQueryParam() throws Exception {
        JsonRequest request = JsonRequest.fromString(HttpMethod.POST, "http://test.test", "{ \"key\":\"value\", \"array\":[ \"1\", \"test\" ] }");
        request.addQueryParam("query1", "value1");
        request.addQueryParam("query2", "value2");
        assertThat(request.queryParams()).hasSize(2);
        assertThat(request.queryParams()).containsExactly(new ApiQueryParam("query1", "value1"), new ApiQueryParam("query2", "value2"));
    }

    private void assertJsonRequestAttributes(JsonRequest request, String expectedName, HttpMethod expectedMethod, String expectedBody) {
        assertThat(request.name()).describedAs("Request Name").isEqualTo(expectedName);
        assertThat(request.url()).describedAs("Request URL").isEqualTo("http://test.test");
        assertThat(request.method()).describedAs("Request Method").isEqualTo(expectedMethod);
        assertThat(request.contentType()).describedAs("Request Content-type").isEqualTo(ContentType.APPLICATION_JSON);
        assertThat(request.headers()).describedAs("Request Headers").isEmpty();
        assertThat(request.queryParams()).describedAs("Request Query Parameters").isEmpty();
        assertThat(request.body()).describedAs("Request Body").isEqualTo(expectedBody);
    }

    private String getValidJsonFile() throws URISyntaxException {
        return "classpath:/rocks/bastion/core/request/test-body.json";
    }

}