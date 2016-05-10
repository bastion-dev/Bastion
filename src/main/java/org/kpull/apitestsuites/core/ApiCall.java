package org.kpull.apitestsuites.core;

import java.util.Objects;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class ApiCall {

    private String name;
    private String description;
    private ApiRequest request;

    public ApiCall(String name, String description, ApiRequest request) {
        setName(name);
        setDescription(description);
        setRequest(request);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        Objects.requireNonNull(description);
        this.description = description;
    }

    public ApiRequest getRequest() {
        return request;
    }

    public void setRequest(ApiRequest request) {
        Objects.requireNonNull(request);
        this.request = request;
    }
}
