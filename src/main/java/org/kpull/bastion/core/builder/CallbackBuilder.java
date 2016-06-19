package org.kpull.bastion.core.builder;

import org.kpull.bastion.core.Callback;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public interface CallbackBuilder<MODEL> extends ExecuteRequestBuilder {

    ExecuteRequestBuilder thenDo(Callback<? super MODEL> callback);

}
