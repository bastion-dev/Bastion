package rocks.bastion.core;

import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class CommonRequestAttributesTest {

    private CommonRequestAttributes commonRequestAttributes;

    @Before
    public void setUp() {
        commonRequestAttributes = new CommonRequestAttributes(HttpMethod.POST, "http://test.test", "Test Body");
    }

    @Test
    public void constructor() throws Exception {
        assertThat(commonRequestAttributes.method()).describedAs("HTTP Method").isEqualTo(HttpMethod.POST);
        assertThat(commonRequestAttributes.url()).describedAs("URL").isEqualTo("http://test.test");
        assertThat(commonRequestAttributes.body()).describedAs("Body").isEqualTo("Test Body");
        assertThat(commonRequestAttributes.name()).describedAs("Name").isEqualTo("POST http://test.test");
        assertThat(commonRequestAttributes.contentType()).describedAs("Content-type").isEqualTo(Optional.of(ContentType.TEXT_PLAIN));
        assertThat(commonRequestAttributes.queryParams()).describedAs("HTTP Query Params").isEmpty();
        assertThat(commonRequestAttributes.headers()).describedAs("HTTP Header").isEmpty();
        assertThat(commonRequestAttributes.routeParams()).describedAs("Route Params").isEmpty();
    }

    @Test
    public void setName() throws Exception {
        commonRequestAttributes.setName("New Name");
        assertThat(commonRequestAttributes.name()).describedAs("Name").isEqualTo("New Name");
    }

    @Test
    public void setMethod() throws Exception {
        commonRequestAttributes.setMethod(HttpMethod.DELETE);
        assertThat(commonRequestAttributes.method()).describedAs("HTTP Method").isEqualTo(HttpMethod.DELETE);
    }

    @Test
    public void setUrl() throws Exception {
        commonRequestAttributes.setUrl("http://newurl.test");
        assertThat(commonRequestAttributes.url()).describedAs("URL").isEqualTo("http://newurl.test");
    }

    @Test
    public void setContentType() throws Exception {
        commonRequestAttributes.setContentType(ContentType.APPLICATION_JSON);
        assertThat(commonRequestAttributes.contentType()).describedAs("Content-type").isEqualTo(Optional.of(ContentType.APPLICATION_JSON));
    }

    @Test
    public void addHeader() throws Exception {
        commonRequestAttributes.addHeader("Name1", "Value1");
        commonRequestAttributes.addHeader("Name2", "Value2");
        assertThat(commonRequestAttributes.headers()).describedAs("HTTP Headers")
                .containsOnly(new ApiHeader("Name1", "Value1"), new ApiHeader("Name2", "Value2"));
    }

    @Test
    public void addQueryParam() throws Exception {
        commonRequestAttributes.addQueryParam("Name1", "Value1");
        commonRequestAttributes.addQueryParam("Name2", "Value2");
        assertThat(commonRequestAttributes.queryParams()).describedAs("HTTP Query Params")
                .containsOnly(new ApiQueryParam("Name1", "Value1"), new ApiQueryParam("Name2", "Value2"));
    }

    @Test
    public void addRouteParam() throws Exception {
        commonRequestAttributes.addRouteParam("Name1", "Value1");
        commonRequestAttributes.addRouteParam("Name2", "Value2");
        assertThat(commonRequestAttributes.routeParams()).describedAs("Route Params")
                .containsOnly(new RouteParam("Name1", "Value1"), new RouteParam("Name2", "Value2"));
    }

    @Test
    public void setBody() throws Exception {
        commonRequestAttributes.setBody("New Body");
        assertThat(commonRequestAttributes.body()).describedAs("Body").isEqualTo("New Body");
    }

    @Test
    public void setTimeout() throws Exception {
        commonRequestAttributes.setTimeout(1000L);
        assertThat(commonRequestAttributes.timeout()).describedAs("Timeout").isEqualTo(1000L);

    }
}