package org.kpull.bastion.core;

import org.kpull.bastion.runner.ExecutionContext;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
@FunctionalInterface
public interface Assertions<M> {

    void assertions(int statusCode, M model);

}
