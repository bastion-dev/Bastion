package rocks.bastion.core;

import org.apache.http.entity.ContentType;

import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;

public interface Response {
    Optional<ContentType> getContentType();

    int getStatusCode();

    String getStatusText();

    Collection<ApiHeader> getHeaders();

    /**
     * Gets the response's body, ready for reading. The implementation does not guarantee that it will always return the
     * state of the input stream
     *
     * @return The body content sent by the remote end during this HTTP call
     */
    InputStream getBody();
}
