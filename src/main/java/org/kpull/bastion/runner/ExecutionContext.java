package org.kpull.bastion.runner;

import com.fasterxml.jackson.databind.JsonNode;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.request.HttpRequest;
import org.kpull.bastion.core.ApiCall;
import org.kpull.bastion.core.ApiEnvironment;
import org.kpull.bastion.core.ApiRequest;
import org.kpull.bastion.core.ApiResponse;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class ExecutionContext {

    private ApiEnvironment environment;
    private ApiCall call;
    private ApiRequest request;
    private ApiResponse response;
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;
    private JsonNode jsonResponseBody;
    private Object responseModel;

    public ApiEnvironment getEnvironment() {
        return environment;
    }

    void setEnvironment(ApiEnvironment environment) {
        this.environment = environment;
    }

    public ApiCall getCall() {
        return call;
    }

    void setCall(ApiCall call) {
        this.call = call;
    }

    public ApiRequest getRequest() {
        return request;
    }

    void setRequest(ApiRequest request) {
        this.request = request;
    }

    public ApiResponse getResponse() {
        return response;
    }

    void setResponse(ApiResponse response) {
        this.response = response;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    void setHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    void setHttpResponse(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    public Object getResponseModel() {
        return responseModel;
    }

    void setResponseModel(Object responseModel) {
        this.responseModel = responseModel;
    }

    public JsonNode getJsonResponseBody() {
        return jsonResponseBody;
    }

    void setJsonResponseBody(JsonNode jsonResponseBody) {
        this.jsonResponseBody = jsonResponseBody;
    }
}
