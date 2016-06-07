package org.kpull.bastion.support;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kpull.bastion.core.Bastion;
import org.kpull.bastion.runner.BastionRunner;
import org.kpull.bastion.support.embedded.Sushi;
import org.kpull.bastion.support.embedded.TestWithEmbeddedServer;

@RunWith(BastionRunner.class)
public class CreateSushiTest extends TestWithEmbeddedServer {

    @Test
    public void testCreateSushi_Success() {
        Bastion.call("Successfully create sushi", new CreateSushiRequest())
               .bindToModel(Sushi.class)
               .thenAssert((statusCode, model, context) -> {
                   // whatever
               });
    }

}
