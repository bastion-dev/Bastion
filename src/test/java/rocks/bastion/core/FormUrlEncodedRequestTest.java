package rocks.bastion.core;

import com.google.common.collect.Lists;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class FormUrlEncodedRequestTest {

    @Test
    public void post_withDataParameters_shouldReturnARequest() throws Exception {
        FormUrlEncodedRequest request = FormUrlEncodedRequest.post("http://test.test");
        request.addDataParameter("name", "John");
        request.addDataParameter("surname", "Doe");
        assertFormUrlEncodedRequestAttributes(request, "POST http://test.test", HttpMethod.POST, "name=John&surname=Doe");
    }

    @Test
    public void put_withDataParameters_shouldReturnARequest() throws Exception {
        FormUrlEncodedRequest request = FormUrlEncodedRequest.put("http://test.test");
        request.addDataParameters(Lists.newArrayList(new ApiDataParameter("name", "John"), new ApiDataParameter("surname", "Doe")));
        assertFormUrlEncodedRequestAttributes(request, "PUT http://test.test", HttpMethod.PUT, "name=John&surname=Doe");
    }

    @Test
    public void delete_withDataParameters_shouldReturnARequest() throws Exception {
        FormUrlEncodedRequest request = FormUrlEncodedRequest.delete("http://test.test");
        request.addDataParameters(Lists.newArrayList(new ApiDataParameter("name", "John"), new ApiDataParameter("surname", "Doe")));
        assertFormUrlEncodedRequestAttributes(request, "DELETE http://test.test", HttpMethod.DELETE, "name=John&surname=Doe");
    }

    @Test
    public void patch_withDataParameters_shouldReturnARequest() throws Exception {
        FormUrlEncodedRequest request = FormUrlEncodedRequest.patch("http://test.test");
        request.addDataParameters(Lists.newArrayList(new ApiDataParameter("name", "John"), new ApiDataParameter("surname", "Doe")));
        assertFormUrlEncodedRequestAttributes(request, "PATCH http://test.test", HttpMethod.PATCH, "name=John&surname=Doe");
    }

    @Test
    public void withMethod_withDataParameters_shouldReturnARequest() throws Exception {
        FormUrlEncodedRequest request = FormUrlEncodedRequest.withMethod(HttpMethod.POST, "http://test.test");
        request.addDataParameters(Lists.newArrayList(new ApiDataParameter("name", "John"), new ApiDataParameter("surname", "Doe")));
        assertFormUrlEncodedRequestAttributes(request, "POST http://test.test", HttpMethod.POST, "name=John&surname=Doe");
    }

    @Test
    public void overrideContentType() throws Exception {
        FormUrlEncodedRequest request = FormUrlEncodedRequest.withMethod(HttpMethod.POST, "http://test.test");
        request.overrideContentType(ContentType.TEXT_PLAIN);
        assertThat(request.contentType()).isEqualTo(Optional.ofNullable(ContentType.TEXT_PLAIN));
    }

    @Test
    public void addHeader() throws Exception {
        FormUrlEncodedRequest request = FormUrlEncodedRequest.withMethod(HttpMethod.POST, "http://test.test");
        request.addHeader("header1", "value1");
        request.addHeader("header2", "value2");
        assertThat(request.headers()).hasSize(2);
        assertThat(request.headers()).containsExactly(new ApiHeader("header1", "value1"), new ApiHeader("header2", "value2"));
    }

    @Test
    public void addQueryParam() throws Exception {
        FormUrlEncodedRequest request = FormUrlEncodedRequest.withMethod(HttpMethod.POST, "http://test.test");
        request.addQueryParam("query1", "value1");
        request.addQueryParam("query2", "value2");
        assertThat(request.queryParams()).hasSize(2);
        assertThat(request.queryParams()).containsExactly(new ApiQueryParam("query1", "value1"), new ApiQueryParam("query2", "value2"));
    }

    private void assertFormUrlEncodedRequestAttributes(FormUrlEncodedRequest request, String expectedName, HttpMethod expectedMethod, String expectedBody) {
        assertThat(request.name()).describedAs("Request Name").isEqualTo(expectedName);
        assertThat(request.url()).describedAs("Request URL").isEqualTo("http://test.test");
        assertThat(request.method()).describedAs("Request Method").isEqualTo(expectedMethod);
        assertThat(request.contentType().isPresent()).describedAs("Request Content-type is present").isTrue();
        assertThat(request.contentType().get()).describedAs("Request Content-type").isEqualTo(ContentType.APPLICATION_FORM_URLENCODED);
        assertThat(request.headers()).describedAs("Request Headers").isEmpty();
        assertThat(request.queryParams()).describedAs("Request Query Parameters").isEmpty();
        assertThat(request.body()).describedAs("Request Body").isEqualTo(expectedBody);
    }

}