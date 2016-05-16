package org.kpull.apitestsuites.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class ApiResponse {

    private List<ApiHeader> headers;
    private int statusCode;
    private String type;
    private String body;

    public ApiResponse(List<ApiHeader> headers, int statusCode, String type, String body) {
        setHeaders(headers);
        setStatusCode(statusCode);
        setType(type);
        setBody(body);
    }

    public List<ApiHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(List<ApiHeader> headers) {
        Objects.requireNonNull(headers);
        this.headers = new LinkedList<>(headers);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        Objects.requireNonNull(type);
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        Objects.requireNonNull(body);
        this.body = body;
    }
}