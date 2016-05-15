package org.kpull.apitestsuites.junit;

import org.junit.runner.Description;
import org.kpull.apitestsuites.core.ApiSuite;

import java.util.Objects;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public final class ApiSuiteDescription {

    private ApiSuiteDescription() {
    }

    public static Description describe(ApiSuite apiSuite) {
        Objects.requireNonNull(apiSuite);
        String suiteName = apiSuite.getName();
        Description suiteDescription = Description.createSuiteDescription(suiteName);
        apiSuite.getApiCalls().forEach(apiCall -> suiteDescription.addChild(Description.createTestDescription(suiteName, apiCall.getName())));
        return suiteDescription;
    }
}
