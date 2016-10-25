package rocks.bastion.support;

import org.junit.Test;
import rocks.bastion.Bastion;
import rocks.bastion.core.GeneralRequest;
import rocks.bastion.support.embedded.TestWithEmbeddedServer;

import static org.assertj.core.api.Assertions.assertThat;

public class ChangeRestaurantNameTest extends TestWithEmbeddedServer {

    @Test
    public void testPost() {
        // docs:general-post-example
        Bastion.request("Change the Restaurant's Name", GeneralRequest.post("http://localhost:9876/restaurant", "The Fish Parlour"))
                .withAssertions((statusCode, response, model) -> assertThat(model).isEqualTo("The Fish Parlour"))
                .call();
        // docs:general-post-example
    }

}
