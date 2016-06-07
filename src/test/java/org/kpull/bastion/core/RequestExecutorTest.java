package org.kpull.bastion.core;

import org.junit.Test;
import org.kpull.bastion.support.CreateSushiRequest;
import org.kpull.bastion.support.embedded.TestWithEmbeddedServer;

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