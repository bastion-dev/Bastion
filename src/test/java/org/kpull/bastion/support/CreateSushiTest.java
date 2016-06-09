package org.kpull.bastion.support;

import org.junit.Test;
import org.kpull.bastion.core.Bastion;
import org.kpull.bastion.support.embedded.Sushi;
import org.kpull.bastion.support.embedded.TestWithEmbeddedServer;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(BastionRunner.class)
public class CreateSushiTest extends TestWithEmbeddedServer {

    @Test
    public void testCreateSushi_Success() {
        Bastion.api("Successfully create sushi", new CreateSushiRequest())
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
}
