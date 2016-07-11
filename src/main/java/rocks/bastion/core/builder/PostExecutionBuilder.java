package rocks.bastion.core.builder;

import rocks.bastion.core.ModelResponse;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public interface PostExecutionBuilder<MODEL> {

    MODEL getModel();

    ModelResponse<? extends MODEL> getResponse();

}
