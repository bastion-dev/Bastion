package org.kpull.apitestsuites.core;

import java.util.Objects;
import java.util.Optional;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class ApiCall {

    private String name;
    private String description;
    private ApiRequest request;
    private ApiResponse response;
    private Class<?> responseModel;
    private String postCallScript;

    public ApiCall(String name, String description, ApiRequest request, ApiResponse response, Class<?> responseModel, String postCallScript) {
        setName(name);
        setDescription(description);
        setRequest(request);
        setResponse(response);
        setResponseModel(responseModel);
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

    public Optional<Class<?>> getResponseModel() {
        return Optional.ofNullable(responseModel);
    }

    public void setResponseModel(Class<?> responseModel) {
        this.responseModel = responseModel;
    }

    public String getPostCallScript() {
        return postCallScript;
    }

    public void setPostCallScript(String postCallScript) {
        Objects.requireNonNull(postCallScript);
        this.postCallScript = postCallScript;
    }
}