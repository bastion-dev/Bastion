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

    private Request requestToExecute;

    public RequestExecutor(Request requestToExecute) {
        Objects.requireNonNull(requestToExecute);
        this.requestToExecute = requestToExecute;
    }

    public Response execute() {
        try {
            HttpRequest httpRequest;
            switch (requestToExecute.method().getValue()) {
                case "POST":
                    httpRequest = Unirest.post(requestToExecute.url());
                    break;
                case "GET":
                    httpRequest = Unirest.get(requestToExecute.url());
                    break;
                case "PUT":
                    httpRequest = Unirest.put(requestToExecute.url());
                    break;
                case "DELETE":
                    httpRequest = Unirest.delete(requestToExecute.url());
                    break;
                case "OPTIONS":
                    httpRequest = Unirest.options(requestToExecute.url());
                    break;
                case "HEAD":
                    httpRequest = Unirest.head(requestToExecute.url());
                    break;
                case "PATCH":
                    httpRequest = Unirest.patch(requestToExecute.url());
                    break;
                default:
                    httpRequest = null;
            }
            requestToExecute.headers().stream().forEach(header -> httpRequest.queryString(header.getName(), header.getValue()));
            requestToExecute.queryParams().stream().forEach(queryParam -> httpRequest.queryString(queryParam.getName(), queryParam.getValue()));
            if (httpRequest instanceof HttpRequestWithBody) {
                if (requestToExecute.contentType().equals(ContentType.APPLICATION_JSON)) {
                    ((HttpRequestWithBody) httpRequest).body(new Gson().toJson(requestToExecute.body()));
                } else {
                    ((HttpRequestWithBody) httpRequest).body(requestToExecute.body().toString());
                }
            }
            HttpResponse<InputStream> httpResponse = httpRequest.asBinary();
            return new Response(httpResponse.getStatus(),
                    httpResponse.getStatusText(),
                    httpResponse.getHeaders().entrySet().stream().flatMap(header ->
                            header.getValue().stream().map(headerValue ->
                                    new ApiHeader(header.getKey(), headerValue))).collect(Collectors.toList()),
                    httpResponse.getBody());
        } catch (UnirestException exception) {
            throw new IllegalStateException("Failed executing request", exception);
        }
    }

}
