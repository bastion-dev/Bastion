package rocks.bastion.core.configuration;

import org.junit.Before;
import org.junit.Test;
import rocks.bastion.Bastion;
import rocks.bastion.core.ApiHeader;
import rocks.bastion.core.ApiQueryParam;
import rocks.bastion.core.BastionFactory;
import rocks.bastion.core.RouteParam;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
    
public class BastionConfigurationTest {

    @Before
    public void before() {
        BastionFactory.setConfiguration(new Configuration());
    }

    @Test
    public void programmaticConfiguration_bastionConfigured() {
        Bastion.globals()
                .addHeader("header1", "headerValue1")
                .addHeader("header1", "headerValue2")
                .addHeader("header2", "headerValue1")
                .addQueryParam("queryParam1", "queryParamValue1")
                .addRouteParam("routeParam1", "value1")
                .timeout(15000);

        Configuration config = BastionFactory.getConfiguration();
        assertThat(config).isNotNull();

        GlobalRequestAttributes globals = config.getGlobalRequestAttributes();
        assertThat(globals).isNotNull();

        Collection<ApiHeader> headers = globals.getGlobalHeaders();
        assertThat(headers).isNotNull();
        assertThat(headers).containsExactly(new ApiHeader("header1", "headerValue1"), new ApiHeader("header1", "headerValue2"), new ApiHeader("header2", "headerValue1"));

        Collection<ApiQueryParam> queryParams = globals.getGlobalQueryParams();
        assertThat(queryParams).isNotNull();
        assertThat(queryParams).containsExactly(new ApiQueryParam("queryParam1", "queryParamValue1"));

        Collection<RouteParam> routeParams = globals.getGlobalRouteParams();
        assertThat(routeParams).isNotNull();
        assertThat(routeParams).containsExactly(new RouteParam("routeParam1", "value1"));

        assertThat(globals.getGlobalRequestTimeout()).isEqualTo(15000);
    }

    @Test
    public void resourceConfiguration_bastionConfigured() throws Exception{
        Configuration config = Bastion.load("bastion.yml");
        assertThat(config).isNotNull();

        GlobalRequestAttributes globals = config.getGlobalRequestAttributes();
        assertThat(globals).isNotNull();

        Collection<ApiHeader> headers = globals.getGlobalHeaders();
        assertThat(headers).isNotNull();
        assertThat(headers).containsExactly(new ApiHeader("header1", "headerValue1"), new ApiHeader("header1", "headerValue2"), new ApiHeader("header2", "headerValue1"));

        Collection<ApiQueryParam> queryParams = globals.getGlobalQueryParams();
        assertThat(queryParams).isNotNull();
        assertThat(queryParams).containsExactly(new ApiQueryParam("queryParam1", "queryParamValue1"));

        Collection<RouteParam> routeParams = globals.getGlobalRouteParams();
        assertThat(routeParams).isNotNull();
        assertThat(routeParams).containsExactly(new RouteParam("routeParam1", "value1"));

        assertThat(globals.getGlobalRequestTimeout()).isEqualTo(15000);
    }
}