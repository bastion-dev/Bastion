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

    private String name;
    private ApiEnvironment environment;
    private List<ApiCall> apiCall;

    public ApiSuite() {
        this("Empty API Suite", new ApiEnvironment(), new LinkedList<>());
    }

    public ApiSuite(String name, ApiEnvironment environment, List<ApiCall> apiCall) {
        setName(name);
        setEnvironment(environment);
        setApiCall(apiCall);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Objects.requireNonNull(name);
        this.name = name;
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
        this.apiCall = new LinkedList<>(apiCall);
    }
}
