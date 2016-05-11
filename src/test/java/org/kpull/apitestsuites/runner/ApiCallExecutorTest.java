package org.kpull.apitestsuites.runner;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.kpull.apitestsuites.core.*;

import java.util.Collections;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.data.MapEntry.entry;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class ApiCallExecutorTest {

    private ApiRequest createApiRequest() {
        return new ApiRequest("GET", "http://api.openweathermap.org/data/2.5/weather", Collections.emptyList(),
                "application/json", "", Lists.newArrayList(new ApiQueryParam("q", "London"), new ApiQueryParam("APPID", "{{APPID}}")));
    }

    private ApiCall createApiCall() {
        return new ApiCall("Get London's Current Weather", "Get the current weather conditions in London, UK.", createApiRequest(),
                createApiResponse(), "environment.putObject('dt', httpResponse.body.object.get('dt'));");
    }

    private ApiSuite createApiSuite() {
        return new ApiSuite(createEnvironment(), Lists.newArrayList(createApiCall()));
    }

    private ApiEnvironment createEnvironment() {
        return new ApiEnvironment(Maps.newHashMap(Collections.singletonMap("APPID", System.getProperty("WeatherApiKey"))));
    }

    private ApiResponse createApiResponse() {
        return new ApiResponse(Collections.emptyList(), "", "");
    }

    @Test
    public void execute() throws Exception {
        ApiSuite apiSuite = createApiSuite();
        ApiCall apiCallToExecute = apiSuite.getApiCall().get(0);
        ApiCallExecutor executor = new ApiCallExecutor(apiSuite.getEnvironment(), apiCallToExecute);
        executor.execute();
        assertThat(apiCallToExecute.getResponse().getBody()).isNotEmpty();
        assertThat(apiSuite.getEnvironment()).contains(entry("dt", "1463001767"));
    }

}