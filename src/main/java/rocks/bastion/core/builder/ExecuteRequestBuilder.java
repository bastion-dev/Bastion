package rocks.bastion.core.builder;

import rocks.bastion.core.ModelResponse;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public interface ExecuteRequestBuilder<MODEL> {

    ModelResponse<MODEL> call();

}
