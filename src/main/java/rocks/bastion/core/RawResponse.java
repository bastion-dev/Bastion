package rocks.bastion.core;

import org.apache.http.entity.ContentType;

import java.io.InputStream;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class RawResponse implements Response {

    private int statusCode;
    private String statusText;
    private Collection<ApiHeader> headers;
    private InputStream body;

    public RawResponse(int statusCode, String statusText, Collection<ApiHeader> headers, InputStream body) {
        Objects.requireNonNull(statusCode);
        Objects.requireNonNull(statusText);
        Objects.requireNonNull(headers);
        Objects.requireNonNull(body);
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.headers = headers;
        this.body = body;
    }

    @Override
    public Optional<ContentType> getContentType() {
        return headers.stream().filter(header -> header.getName().equalsIgnoreCase("content-type")).findFirst().map(header -> ContentType.parse(header.getValue()));
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getStatusText() {
        return statusText;
    }

    @Override
    public Collection<ApiHeader> getHeaders() {
        return headers;
    }

    @Override
    public InputStream getBody() {
        return body;
    }
}
