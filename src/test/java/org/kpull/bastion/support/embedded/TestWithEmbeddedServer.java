package org.kpull.bastion.support.embedded;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import static java.lang.String.format;

/**
 * To be extended by tests which will require a testing web service to make REST API calls to.
 * The web service will be started on port 9876 and stopped before and after all tests are run.
 */
public abstract class TestWithEmbeddedServer {

    private static final String host = "http://localhost";
    private static final int port = 9876;
    private static final String path = "/sushi";

    private static SushiService sushiService;

    protected String getUrl() {
        return format("%s:%s%s", host, port, path);
    }

    @BeforeClass
    public static void startService() {
        sushiService = new SushiService(port);
        sushiService.start();
    }

    @AfterClass
    public static void stopService() {
        sushiService.stop();
    }
}