package org.kpull.bastion.support.embedded;

import org.junit.After;
import org.junit.Before;

/**
 * To be extended by tests which will require a testing web service to make REST API calls to.
 * The web service will be started and stopped on the provided port before and and after each test method.
 */
public abstract class TestWithEmbeddedServer {

    protected abstract int getEmbeddedServerPort();

    private SushiService sushiService;

    @Before
    public  void before() {
        sushiService = new SushiService(getEmbeddedServerPort());
        sushiService.start();
    }

    @After
    public void after() {
        sushiService.stop();
    }
}