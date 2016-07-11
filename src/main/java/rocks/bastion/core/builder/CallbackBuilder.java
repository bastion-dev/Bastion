package rocks.bastion.core.builder;

import rocks.bastion.core.Callback;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public interface CallbackBuilder<MODEL> extends ExecuteRequestBuilder<MODEL> {

    ExecuteRequestBuilder<? extends MODEL> thenDo(Callback<? super MODEL> callback);

}
