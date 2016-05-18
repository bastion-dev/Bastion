package org.kpull.bastion.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.jglue.fluentjson.JsonBuilderFactory;
import org.junit.Test;
import org.kpull.bastion.core.ApiCall;
import org.kpull.bastion.core.ApiSuite;
import org.kpull.bastion.core.ApiSuiteBuilder;
import org.kpull.bastion.support.WeatherModel;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.data.MapEntry.entry;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class ApiCallExecutorTest {

    private ApiSuite createApiSuite() {
        // @formatter:off
        return ApiSuiteBuilder.start()
                .name("Open Weather API")
                .environment()
                    .entry("APPID", System.getProperty("WeatherApiKey"))
                    .entry("BIN_ID", System.getProperty("BinId"))
                    .entry("TestEnv", "Env50")
                    .done()
                .call()
                    .name("Get London's Current Weather")
                    .description("Get the current weather conditions in London, UK.")
                    .request()
                        .method("GET")
                        .url("http://api.openweathermap.org/data/2.5/weather")
                        .type("application/json")
                        .queryParam("q", "London")
                        .queryParam("APPID", "{{APPID}}")
                        .done()
                    .responseModel(WeatherModel.class)
                        .assertions((statusCode, model, context) -> {
                            assertThat(statusCode).isEqualTo(HttpStatus.SC_OK);
                            assertThat(model.getDt()).isNotEmpty();
                        })
                    .afterwardsExecute()
                        .groovy(
                            "environment.putObject('lat', jsonResponseBody.get('coord').get('lat'));" +
                            "System.out.println(model);"
                        )
                    .done()
                .call()
                    .name("Post New Weather Forecast")
                    .description("Change the weather forecast for a number of different cities around the world")
                    .request()
                        .method("POST")
                        .url("http://requestb.in/{{BIN_ID}}")
                        .type("application/json")
                        .queryParam("BIN_ID", "{{BIN_ID}}")
                        .bodyFromJsonObject(json -> json.add("city", "London")
                            .add("forecast", "sunny")
                            .addObject("country")
                                .add("code", "GB")
                                .add("name", "United Kingdom")
                                .add("comment", "{{TestEnv}}")
                                .end()
                            .toString())
                        .done()
                    .done()
                .build();
        // @formatter:on
    }

    @Test
    public void executeWeatherCall() throws Exception {
        ApiSuite apiSuite = createApiSuite();
        ApiCall apiCallToExecute = apiSuite.getApiCalls().get(0);
        ApiCallExecutor executor = new ApiCallExecutor(apiSuite.getEnvironment(), apiCallToExecute, new ObjectMapper());
        executor.execute();
        assertThat(apiCallToExecute.getResponse().getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(apiCallToExecute.getResponse().getBody()).isNotEmpty();
        assertThat(apiSuite.getEnvironment()).contains(entry("lat", "51.51"));
    }

    @Test
    public void executePostJsonCall() throws Exception {
        ApiSuite apiSuite = createApiSuite();
        ApiCall apiCallToExecute = apiSuite.getApiCalls().get(1);
        ApiCallExecutor executor = new ApiCallExecutor(apiSuite.getEnvironment(), apiCallToExecute, new ObjectMapper());
        executor.execute();
        assertThat(apiCallToExecute.getRequest().getBody()).isEqualTo(JsonBuilderFactory.buildObject()
                // @formatter:off
                .add("city", "London")
                .add("forecast", "sunny")
                    .addObject("country")
                    .add("code", "GB")
                    .add("name", "United Kingdom")
                    .add("comment", "{{TestEnv}}")
                    .end()
                .toString());
                // @formatter:on
        assertThat(apiCallToExecute.getResponse().getStatusCode()).isEqualTo(HttpStatus.SC_OK);
    }

}