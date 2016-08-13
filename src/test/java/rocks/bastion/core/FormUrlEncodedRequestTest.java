package rocks.bastion.core;

import com.google.common.collect.Lists;
import org.apache.http.entity.ContentType;
import org.junit.Test;

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