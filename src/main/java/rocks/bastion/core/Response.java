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

    InputStream getBody();
}
