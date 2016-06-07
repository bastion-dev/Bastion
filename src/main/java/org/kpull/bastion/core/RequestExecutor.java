package org.kpull.bastion.core;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.apache.http.entity.ContentType;
import org.kpull.bastion.external.Request;

import java.io.InputStream;
import java.util.Objects;
import java.util.stream.Collectors;

public class RequestExecutor {

    private Request bastionRequest;
    private HttpRequest executableRequest;

    public RequestExecutor(Request bastionRequest) {
        Objects.requireNonNull(bastionRequest);
        this.bastionRequest = bastionRequest;
        this.executableRequest = identifyHttpRequest();
    }

    public Response execute() {
        try {
            applyHeaders();
            applyQueryParameters();
            applyBody();
            HttpResponse<InputStream> httpResponse = performRequest();
            return convertToBastionResponse(httpResponse);
        } catch (UnirestException exception) {
            throw new IllegalStateException("Failed executing request", exception);
        }
    }

    private HttpRequest identifyHttpRequest() {
        switch (bastionRequest.method().getValue()) {
            case "GET":
                return Unirest.get(bastionRequest.url());
            case "POST":
                return Unirest.post(bastionRequest.url());
            case "PATCH":
                return Unirest.patch(bastionRequest.url());
            case "DELETE":
                return Unirest.delete(bastionRequest.url());
            case "PUT":
                return Unirest.put(bastionRequest.url());
            case "OPTIONS":
                return Unirest.options(bastionRequest.url());
            case "HEAD":
                return Unirest.head(bastionRequest.url());
            default:
                return null;
        }
    }

    private void applyHeaders() {
        bastionRequest.headers().stream().forEach(header -> executableRequest.header(header.getName(), header.getValue()));
    }

    private void applyQueryParameters() {
        bastionRequest.queryParams().stream().forEach(queryParam -> executableRequest.queryString(queryParam.getName(), queryParam.getValue()));
    }

    private void applyBody() {
        if (executableRequest instanceof HttpRequestWithBody) {
            if (bastionRequest.contentType().equals(ContentType.APPLICATION_JSON)) {
                ((HttpRequestWithBody) executableRequest).body(new Gson().toJson(bastionRequest.body()));
            } else {
                ((HttpRequestWithBody) executableRequest).body(bastionRequest.body().toString());
            }
        }
    }

    private HttpResponse<InputStream> performRequest() throws UnirestException {
        return executableRequest.asBinary();
    }

    private Response convertToBastionResponse(HttpResponse<InputStream> httpResponse) {
        return new Response(httpResponse.getStatus(),
                httpResponse.getStatusText(),
                httpResponse.getHeaders().entrySet().stream().flatMap(header ->
                        header.getValue().stream().map(headerValue ->
                                new ApiHeader(header.getKey(), headerValue))).collect(Collectors.toList()),
                httpResponse.getBody());
    }
}
