package rocks.bastion.core;

import org.junit.Test;
import rocks.bastion.support.CreateSushiRequest;
import rocks.bastion.support.embedded.TestWithEmbeddedServer;

import static org.junit.Assert.assertEquals;

/**
 * Created by ChiaraFSC on 07/06/2016.
 */
public class RequestExecutorTest extends TestWithEmbeddedServer {

    @Test
    public void testPostExecute() {
        Response response = new RequestExecutor(new CreateSushiRequest()).execute();
        assertEquals(201, response.getStatusCode());
    }
}