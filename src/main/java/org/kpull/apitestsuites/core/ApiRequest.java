package org.kpull.apitestsuites.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class ApiRequest {

    private String method;
    private String url;
    private List<ApiHeader> headers;
    private List<ApiQueryParam> queryParams;
    private String type;
    private String body;

    public ApiRequest(String method, String url, List<ApiHeader> headers, String type, String body, List<ApiQueryParam> queryParams) {
        setMethod(method);
        setUrl(url);
        setHeaders(headers);
        setQueryParams(queryParams);
        setType(type);
        setBody(body);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        Objects.requireNonNull(url);
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        Objects.requireNonNull(method);
        this.method = method;
    }

    public List<ApiHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(List<ApiHeader> headers) {
        Objects.requireNonNull(headers);
        this.headers = new LinkedList<>(headers);
    }

    public List<ApiQueryParam> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(List<ApiQueryParam> queryParams) {
        Objects.requireNonNull(queryParams);
        this.queryParams = new LinkedList<>(queryParams);
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
