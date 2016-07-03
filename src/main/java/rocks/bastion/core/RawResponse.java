package rocks.bastion.core;

import com.google.common.io.ByteStreams;
import org.apache.http.entity.ContentType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class RawResponse implements Response {

    private int statusCode;
    private String statusText;
    private Collection<ApiHeader> headers;
    private byte[] bodyContent;

    public RawResponse(int statusCode, String statusText, Collection<ApiHeader> headers, InputStream body) {
        try {
            Objects.requireNonNull(statusCode);
            Objects.requireNonNull(statusText);
            Objects.requireNonNull(headers);
            Objects.requireNonNull(body);
            this.statusCode = statusCode;
            this.statusText = statusText;
            this.headers = headers;
            bodyContent = ByteStreams.toByteArray(body);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading the body input stream", e);
        }
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
        return new ByteArrayInputStream(bodyContent);
    }
}
