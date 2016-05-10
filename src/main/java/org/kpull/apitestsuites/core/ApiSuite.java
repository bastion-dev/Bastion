package org.kpull.apitestsuites.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Declares an environment and a collection of HTTP API calls
 *
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class ApiSuite {

    private ApiEnvironment environment;
    private List<ApiCall> apiCall;

    public ApiSuite() {
        environment = new ApiEnvironment();
        apiCall = new LinkedList<>();
    }

    public ApiSuite(ApiEnvironment environment, List<ApiCall> apiCall) {
        this.environment = environment;
        this.apiCall = apiCall;
    }

    public ApiEnvironment getEnvironment() {
        return environment;
    }

    public void setEnvironment(ApiEnvironment environment) {
        Objects.requireNonNull(environment);
        this.environment = environment;
    }

    public List<ApiCall> getApiCall() {
        return apiCall;
    }

    public void setApiCall(List<ApiCall> apiCall) {
        Objects.requireNonNull(apiCall);
        this.apiCall = apiCall;
    }
}
