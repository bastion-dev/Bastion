package rocks.bastion.runner;

import com.fasterxml.jackson.databind.JsonNode;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.request.HttpRequest;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class ExecutionContext {

    private HttpRequest httpRequest;
    private HttpResponse httpResponse;
    private JsonNode jsonResponseBody;
    private Object responseModel;

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
