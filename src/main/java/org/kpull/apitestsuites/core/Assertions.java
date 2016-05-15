package org.kpull.apitestsuites.core;

import org.kpull.apitestsuites.runner.ExecutionContext;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
@FunctionalInterface
public interface Assertions<M extends Object> {

    void assertions(M model, ExecutionContext context);

}
