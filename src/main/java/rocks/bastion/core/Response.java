package rocks.bastion.core;

import org.apache.http.entity.ContentType;

import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;

/**
 * Defines an HTTP response object containing a status code and associated status text. The response object also contains
 * the HTTP response headers returned by the server. If one of the headers is the {@code Content-type} header, the
 * content-type header is available by using the {@link #getContentType()} method.
 */
public interface Response {

    /**
     * Gets the value of the {@code Content-type} header that was returned by the server. This same value is also
     * available as part of the headers returned by the {@link #getHeaders()} methods.
     *
     * @return The value of the {@code Content-type} header in this HTTP response. If there's no {@code Content-type}
     * header, an {@link Optional#empty() empty Optional} is returned.
     */
    Optional<ContentType> getContentType();

    /**
     * Gets the HTTP status code in this response. This is an integer value as described on
     * <a href="https://en.wikipedia.org/wiki/List_of_HTTP_status_codes">this page</a>.
     *
     * @return The HTTP status code
     */
    int getStatusCode();

    /**
     * The text description of the HTTP status code returned by {@link #getStatusCode()}.
     *
     * @return The HTTP status text
     */
    String getStatusText();

    /**
     * Returns the collection of HTTP header key-value pairs in this response. This method will always
     * return a non-{@literal null} collection which could possibly be empty if no headers were returned by the
     * server.
     *
     * @return The collection of HTTP headers
     */
    Collection<ApiHeader> getHeaders();

    /**
     * Gets the response's body, ready for reading. The input stream returned by this method will always be positioned at
     * the start of the response body and each different invocation of this method will return a new, independent instance
     * of the body input stream.
     *
     * @return The body content sent by the remote end during this HTTP call
     */
    InputStream getBody();
}
