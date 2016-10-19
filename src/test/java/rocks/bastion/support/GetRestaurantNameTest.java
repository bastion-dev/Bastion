package rocks.bastion.support;

import org.junit.Test;
import rocks.bastion.Bastion;
import rocks.bastion.core.GeneralRequest;
import rocks.bastion.support.embedded.TestWithEmbeddedServer;

import static org.assertj.core.api.Assertions.assertThat;

public class GetRestaurantNameTest extends TestWithEmbeddedServer {

    @Test
    public void testGet() {
        // docs:general-get-example
        Bastion.request("Get the Restaurant's Name", GeneralRequest.get("http://localhost:9876/restaurant"))
                .withAssertions((statusCode, response, model) -> assertThat(model).isEqualTo("The Sushi Parlour"))
                .call();
        // docs:general-get-example
    }

}
