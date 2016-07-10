package rocks.bastion.support;

import org.junit.Test;
import org.junit.runner.RunWith;
import rocks.bastion.core.Bastion;
import rocks.bastion.core.ModelResponse;
import rocks.bastion.core.json.JsonRequest;
import rocks.bastion.core.json.JsonResponseAssertions;
import rocks.bastion.junit.BastionRunner;
import rocks.bastion.support.embedded.Sushi;
import rocks.bastion.support.embedded.TestWithEmbeddedServer;

@RunWith(BastionRunner.class)
public class CreateAndGetSushiTest extends TestWithEmbeddedServer {

    @Test
    public void secondTestCreateSushi_Success() {
        ModelResponse<Sushi> response = Bastion.request("Create Sushi", JsonRequest.postFromString("http://localhost:9876/sushi", "{ " +
                "\"name\":\"sashimi\", " +
                "\"price\":\"5.60\", " +
                "\"type\":\"SASHIMI\" " +
                "}"
        )).bind(Sushi.class).withAssertions(JsonResponseAssertions.fromString(201, "{ " +
                        "\"id\":5, " +
                        "\"name\":\"sashimi\", " +
                        "\"price\":5.60, " +
                        "\"type\":\"SASHIMI\" " +
                        "}"
                ).ignoreFieldsValues("/id")
        ).call();
        Sushi createdSushi = response.getModel();
        Bastion.request("Get Sushi", )
    }
}
