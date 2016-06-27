package rocks.bastion.external;

import org.apache.http.entity.ContentType;
import rocks.bastion.core.ApiHeader;
import rocks.bastion.core.ApiQueryParam;

import java.util.Collection;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public interface Request {

    String name();

    String url();

    HttpMethod method();

    ContentType contentType();

    Collection<ApiHeader> headers();

    Collection<ApiQueryParam> queryParams();

    Object body();

}
