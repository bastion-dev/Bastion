package org.kpull.bastion.junit;

import org.junit.runner.RunWith;
import org.kpull.bastion.core.Bastion;
import org.kpull.bastion.support.WeatherModel;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
@RunWith(ApiSuiteRunner.class)
public class ApiSuiteRunnerTest {

    @ApiSuite
    public org.kpull.bastion.core.ApiSuite createApiSuite() {
        // @formatter:off
        return Bastion.start()
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
                    .afterwardsExecute()
                        .callback((context, environment) -> {
                            context.getEnvironment().putObject("lat", context.getJsonResponseBody().get("coord").get("lat"));
                            System.out.println(context.getResponseModel());
                        })
                    .done()
                .build();
        // @formatter:on
    }

}
