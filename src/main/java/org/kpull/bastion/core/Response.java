package org.kpull.bastion.core;

import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;

public class Response {

    private int statusCode;
    private String statusText;
    private Collection<ApiHeader> headers;
    private InputStream body;

    public Response(int statusCode, String statusText, Collection<ApiHeader> headers, InputStream body) {
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.headers = headers;
        this.body = body;
    }

    public Optional<ApiHeader> getContentType() {
        return headers.stream().filter(header -> header.getName().equalsIgnoreCase("content-type")).findFirst();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public Collection<ApiHeader> getHeaders() {
        return headers;
    }

    public InputStream getBody() {
        return body;
    }
}
