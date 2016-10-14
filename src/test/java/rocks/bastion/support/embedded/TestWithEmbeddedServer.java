package rocks.bastion.support.embedded;

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

    private static SushiService sushiService;

    @BeforeClass
    public static void startService() {
        sushiService = new SushiService(port);
        sushiService.start();
    }

    @AfterClass
    public static void stopService() {
        sushiService.stop();
    }

    protected String getUrl(String resourcePath) {
        return format("%s:%s%s", host, port, resourcePath);
    }
}