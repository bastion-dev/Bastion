package org.kpull.apitestsuites.junit;

import org.junit.runner.RunWith;
import org.kpull.apitestsuites.core.ApiSuiteBuilder;
import org.kpull.apitestsuites.support.WeatherModel;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
@RunWith(ApiSuiteRunner.class)
public class ApiSuiteRunnerTest {

    @ApiSuite
    public org.kpull.apitestsuites.core.ApiSuite createApiSuite() {
        // @formatter:off
        return ApiSuiteBuilder.start()
                .name("Open Weather API")
                .environment()
                    .entry("APPID", System.getProperty("WeatherApiKey"))
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
                        .assertions((statusCode, model, context) -> assertThat(model.getDt()).isNotEmpty())
                    .postCallScript(
                            "environment.putObject('lat', jsonResponseBody.get('coord').get('lat'));" +
                            "System.out.println(model);"
                    )
                    .done()
                .build();
        // @formatter:on
    }

}
