package org.kpull.bastion.core;

import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class ApiSuiteBuilderFromFileTest {

    @Test
    public void buildApiSuiteWithBodyFromFile() throws URISyntaxException {
        // @formatter:off
        ApiSuite suite = ApiSuiteBuilder.start()
                .name("Test API")
                .call()
                    .name("Test Body From File")
                    .description("This request's body comes from a JSON file")
                    .request()
                        .method("POST")
                        .url("http://test.test")
                        .type("application/json")
                        .bodyFromFile(new File(ApiSuiteBuilderFromFileTest.class.getResource("/json/test.json").toURI()))
                        .done()
                    .done()
                .build();
        // @formatter:on

        assertThat(suite.getApiCalls()).hasSize(1);
        assertThat(suite.getApiCalls().get(0).getRequest().getBody()).isEqualTo("{\n" +
                "  \"name\": \"Kyle\",\n" +
                "  \"country\": {\n" +
                "    \"name\": \"Malta\",\n" +
                "    \"code\": \"MT\"\n" +
                "  }\n" +
                "}");
    }

}
