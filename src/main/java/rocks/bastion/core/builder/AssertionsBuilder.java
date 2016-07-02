package rocks.bastion.core.builder;

import rocks.bastion.core.assertions.Assertions;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public interface AssertionsBuilder<MODEL> extends CallbackBuilder<MODEL> {

    CallbackBuilder<? extends MODEL> withAssertions(Assertions<? super MODEL> assertions);

}
