package rocks.bastion.support;

import org.junit.Test;
import org.junit.runner.RunWith;
import rocks.bastion.core.Bastion;
import rocks.bastion.core.assertions.JsonResponseAssertions;
import rocks.bastion.core.request.JsonRequest;
import rocks.bastion.junit.BastionRunner;
import rocks.bastion.support.embedded.Sushi;
import rocks.bastion.support.embedded.TestWithEmbeddedServer;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(BastionRunner.class)
public class CreateSushiTest extends TestWithEmbeddedServer {

    @Test
    public void testCreateSushi_Success() {
        Bastion.api("SUCCESS", new CreateSushiRequest())
                .bind(Sushi.class)
                .withAssertions((statusCode, response, model) -> {
                    assertThat(response.getContentType().isPresent()).isTrue();
                    assertThat(response.getContentType().get().getMimeType()).isEqualToIgnoringCase("application/json");
                    assertThat(statusCode).isEqualTo(201);
                    assertThat(model.getName()).isEqualTo("happiness");
                })
                .thenDo((statusCode, response, model) -> {
                    // do stuff
                })
                .call();

        Bastion.api("SUCCESS (Again)", new CreateSushiRequest())
                .bind(Sushi.class)
                .withAssertions((statusCode, response, model) -> {
                    assertThat(response.getContentType().isPresent()).isTrue();
                    assertThat(response.getContentType().get().getMimeType()).isEqualToIgnoringCase("application/json");
                    assertThat(statusCode).isEqualTo(201);
                    assertThat(model.getName()).isEqualTo("happiness");
                })
                .thenDo((statusCode, response, model) -> {
                    // do stuff
                })
                .call();
    }

    @Test
    public void secondTestCreateSushi_Success() {
        Bastion.api("First Request", JsonRequest.postFromString("http://localhost:9876/sushi", "{ " +
                "\"name\":\"sashimi\", " +
                "\"price\":\"5.60\", " +
                "\"type\":\"SASHIMI\" " +
                "}"
        )).withAssertions(JsonResponseAssertions.fromString(201, "{ " +
                        "\"id\":5, " +
                        "\"name\":\"sashimi\", " +
                        "\"price\":5.60, " +
                        "\"type\":\"SASHIMI\" " +
                        "}"
                ).ignoreFieldValue("/id")
        ).call();
    }
}
