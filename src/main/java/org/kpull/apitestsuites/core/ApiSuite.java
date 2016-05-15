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
    private List<ApiCall> apiCalls;

    public ApiSuite() {
        this("Empty API Suite", new ApiEnvironment(), new LinkedList<>());
    }

    public ApiSuite(String name, ApiEnvironment environment, List<ApiCall> apiCalls) {
        setName(name);
        setEnvironment(environment);
        setApiCalls(apiCalls);
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

    public List<ApiCall> getApiCalls() {
        return apiCalls;
    }

    public void setApiCalls(List<ApiCall> apiCalls) {
        Objects.requireNonNull(apiCalls);
        this.apiCalls = new LinkedList<>(apiCalls);
    }
}
