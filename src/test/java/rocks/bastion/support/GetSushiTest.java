package rocks.bastion.support;

import org.junit.Test;
import rocks.bastion.Bastion;
import rocks.bastion.core.FormUrlEncodedRequest;
import rocks.bastion.core.GeneralRequest;
import rocks.bastion.core.json.JsonResponseAssertions;
import rocks.bastion.support.embedded.TestWithEmbeddedServer;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class GetSushiTest extends TestWithEmbeddedServer {

    @Test
    public void testGetNigiri() {
        // docs:simple-json-assertion
        Bastion.request("Get Nigiri Info", GeneralRequest.get("http://localhost:9876/nigiri"))
                .withAssertions(JsonResponseAssertions.fromString(200, "{ \"id\":5, \"name\":\"Salmon Nigiri\", \"price\":23.55 }"))
                .call();
        // docs:simple-json-assertion
    }

    @Test
    public void testPostSashimi() {
        // docs:form-url-encoded-request
        Bastion.request("Order Sashimi", FormUrlEncodedRequest.post("http://localhost:9876/sashimi")
                .addDataParameter("quantity", "5")
                .addDataParameter("table", "61")
        ).withAssertions(JsonResponseAssertions.fromString(200, "{ \"id\":5, \"name\":\"Sashimi\", \"price\":5.95 }"))
                .call();
        // docs:form-url-encoded-request
    }

}
