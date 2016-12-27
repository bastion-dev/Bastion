package rocks.bastion.documentation;

import org.junit.Test;
import rocks.bastion.Bastion;
import rocks.bastion.core.json.JsonRequest;
import rocks.bastion.core.json.JsonResponseAssertions;
import rocks.bastion.support.embedded.TestWithEmbeddedServer;
import rocks.bastion.support.embedded.TestWithProxiedEmbeddedServer;

public class UserGuideTest extends TestWithProxiedEmbeddedServer {

    @Test
    public void test() {
        Bastion.request("Create Sushi", JsonRequest.postFromString("http://sushi-shop.test/sushi", "{ " +
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
                ).ignoreValuesForProperties("id")
        ).call();
    }

}
