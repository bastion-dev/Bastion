package org.kpull.bastion.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.apache.commons.lang.StringUtils;
import org.kpull.bastion.core.*;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class ApiCallExecutor {

    private ApiEnvironment environment;
    private ApiCall apiCallToExecute;
    private ObjectMapper objectMapper;

    private ExecutionContext context;

    public ApiCallExecutor(ApiEnvironment environment, ApiCall apiCallToExecute, ObjectMapper objectMapper) {
        Objects.requireNonNull(environment);
        Objects.requireNonNull(apiCallToExecute);
        this.environment = environment;
        this.apiCallToExecute = apiCallToExecute;
        this.objectMapper = objectMapper;
    }

    public ApiEnvironment getEnvironment() {
        return environment;
    }

    public ApiCall getApiCallToExecute() {
        return apiCallToExecute;
    }

    private void initialiseContext() {
        context = new ExecutionContext();
        context.setEnvironment(environment);
        context.setCall(apiCallToExecute);
        context.setRequest(apiCallToExecute.getRequest());
    }

    public void execute() {
        try {
            initialiseContext();
            ApiRequest request = apiCallToExecute.getRequest();
            HttpRequest httpRequest = createHttpRequest(request);
            addBodyToRequest(httpRequest);
            addHeadersToRequest(httpRequest);
            addQueryParamsToRequest(httpRequest);
            HttpResponse<String> httpResponse = httpRequest.asString();
            context.setHttpResponse(httpResponse);
            ApiResponse response = createAndSaveResponse(httpResponse);
            parseAndSaveJson(response);
            Object model = parseAndSaveModel(httpResponse);
            performAssertions(httpResponse, model);
            executePostCallScript();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    private void addQueryParamsToRequest(HttpRequest httpRequest) {
        apiCallToExecute.getRequest().getQueryParams().forEach(queryParam -> {
            httpRequest.queryString(queryParam.getName(), environment.process(queryParam.getValue()));
        });
    }

    private void addHeadersToRequest(HttpRequest httpRequest) {
        apiCallToExecute.getRequest().getHeaders().forEach(header -> {
            httpRequest.header(header.getName(), environment.process(header.getValue()));
        });
    }

    private void addBodyToRequest(HttpRequest httpRequest) {
        if (httpRequest instanceof HttpRequestWithBody) {
            ((HttpRequestWithBody) httpRequest).body(environment.process(apiCallToExecute.getRequest().getBody()));
        }
    }

    private void parseAndSaveJson(ApiResponse response) {
        try {
            if (StringUtils.defaultString(response.getType()).startsWith("application/json")) {
                context.setJsonResponseBody(objectMapper.readTree(response.getBody()));
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Could not parse JSON from response (even though response content type is JSON", ioe);
        }
    }

    private void executePostCallScript() {
        apiCallToExecute.getPostCallExecution().execute(context.getHttpResponse().getStatus(), context.getResponse(), environment, context);
    }

    private void performAssertions(HttpResponse response, Object model) {
        apiCallToExecute.getAssertions().ifPresent(assertions -> {
            if (model == null) {
                throw new AssertionError("A null object was parsed from the API response");
            }
            assertions.assertions(response.getStatus(), model, context);
        });
    }

    private Object parseAndSaveModel(HttpResponse<?> httpResponse) {
        Object model = apiCallToExecute.getResponseModel().map(modelClass -> {
            try {
                Objects.requireNonNull(objectMapper, "Object Mapper must be set before we can deserialize to a model object");
                return objectMapper.reader(modelClass).readValue(httpResponse.getRawBody());
            } catch (IOException e) {
                return null;
            }
        }).orElse(null);
        context.setResponseModel(model);
        return model;
    }

    private ApiResponse createAndSaveResponse(HttpResponse<?> httpResponse) {
        // TODO: Refine the next statement
        String contentType = httpResponse.getHeaders().getFirst("content-type");
        ApiResponse response = new ApiResponse(httpResponse.getHeaders().entrySet().stream().flatMap(header -> header.getValue().stream().map(value -> new ApiHeader(header.getKey(), value))).collect(Collectors.toList()),
                httpResponse.getStatus(), contentType, httpResponse.getBody().toString());
        apiCallToExecute.setResponse(response);
        context.setResponse(response);
        return response;
    }

    private HttpRequest createHttpRequest(ApiRequest request) {
        HttpRequest httpRequest = null;
        switch (request.getMethod()) {
            case "POST":
                httpRequest = Unirest.post(environment.process(request.getUrl()));
                break;
            case "GET":
                httpRequest = Unirest.get(environment.process(request.getUrl()));
                break;
            case "PUT":
                httpRequest = Unirest.put(environment.process(request.getUrl()));
                break;
            case "DELETE":
                httpRequest = Unirest.delete(environment.process(request.getUrl()));
                break;
            case "OPTIONS":
                httpRequest = Unirest.options(environment.process(request.getUrl()));
                break;
            case "HEAD":
                httpRequest = Unirest.head(environment.process(request.getUrl()));
                break;
            case "PATCH":
                httpRequest = Unirest.patch(environment.process(request.getUrl()));
                break;
        }
        context.setHttpRequest(httpRequest);
        return httpRequest;
    }
}
