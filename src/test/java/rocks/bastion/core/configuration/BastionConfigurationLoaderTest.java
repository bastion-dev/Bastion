package rocks.bastion.core.configuration;

import org.junit.Test;
import rocks.bastion.Bastion;
import rocks.bastion.core.ApiHeader;
import rocks.bastion.core.ApiQueryParam;
import rocks.bastion.core.RouteParam;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TODO document class
 */
public class BastionConfigurationLoaderTest {

    @Test
    public void load_allConfigurationLoaded() throws Exception{
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