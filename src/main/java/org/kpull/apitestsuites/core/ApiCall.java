package org.kpull.apitestsuites.core;

import java.util.Objects;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class ApiCall {

    private String name;
    private String description;
    private ApiRequest request;
    private ApiResponse response;
    private String postCallScript;

    public ApiCall(String name, String description, ApiRequest request, ApiResponse response, String postCallScript) {
        setName(name);
        setDescription(description);
        setRequest(request);
        setResponse(response);
        setPostCallScript(postCallScript);
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

    public ApiResponse getResponse() {
        return response;
    }

    public void setResponse(ApiResponse response) {
        Objects.requireNonNull(response);
        this.response = response;
    }

    public String getPostCallScript() {
        return postCallScript;
    }

    public void setPostCallScript(String postCallScript) {
        Objects.requireNonNull(postCallScript);
        this.postCallScript = postCallScript;
    }
}