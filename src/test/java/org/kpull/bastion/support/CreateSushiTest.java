package org.kpull.bastion.support;

import org.junit.Test;
import org.kpull.bastion.core.Bastion;
import org.kpull.bastion.support.embedded.Sushi;
import org.kpull.bastion.support.embedded.TestWithEmbeddedServer;

import static org.assertj.core.api.Assertions.*;
import static org.kpull.bastion.core.Bastion.*;

//@RunWith(BastionRunner.class)
public class CreateSushiTest extends TestWithEmbeddedServer {

    private String someVar;

    @Test
    public void testCreateSushi_Success() {
        Bastion.api("Successfully create sushi", new CreateSushiRequest())
                .bind(Sushi.class)
                .thenAssert((statusCode, model) -> {
                    assertThat(statusCode).isEqualTo(201);
                    assertThat(model.getName()).isEqualTo("happiness");
                })
                .thenDo((statusCode, model) -> {
                    // do stuff
                })
                .call();
    }
}
