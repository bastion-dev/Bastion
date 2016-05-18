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
    private Assertions<?> assertions;
    private Callback postCallExecution;

    public ApiCall(String name, String description, ApiRequest request, ApiResponse response, Class<?> responseModel, Assertions<?> assertions, Callback callback) {
        setName(name);
        setDescription(description);
        setRequest(request);
        setResponse(response);
        setResponseModel(responseModel);
        setAssertions(assertions);
        setPostCallExecution(callback);
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

    public Optional<Assertions<Object>> getAssertions() {
        return Optional.ofNullable((Assertions<Object>) assertions);
    }

    public void setAssertions(Assertions<?> assertions) {
        this.assertions = assertions;
    }

    public Optional<Class<?>> getResponseModel() {
        return Optional.ofNullable(responseModel);
    }

    public void setResponseModel(Class<?> responseModel) {
        this.responseModel = responseModel;
    }

    public Callback getPostCallExecution() {
        return postCallExecution;
    }

    public void setPostCallExecution(Callback callback) {
        Objects.requireNonNull(callback);
        this.postCallExecution = callback;
    }
}