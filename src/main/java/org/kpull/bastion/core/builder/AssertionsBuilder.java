package org.kpull.bastion.core.builder;

import org.kpull.bastion.core.Assertions;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public interface AssertionsBuilder<MODEL> extends CallbackBuilder<MODEL> {

    CallbackBuilder<? extends MODEL> withAssertions(Assertions<? super MODEL> assertions);

}
