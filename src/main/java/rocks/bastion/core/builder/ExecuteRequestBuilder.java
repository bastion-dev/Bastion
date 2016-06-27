package rocks.bastion.core.builder;

import rocks.bastion.core.Response;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public interface ExecuteRequestBuilder {

    void call();

    Response getResponse();

}
