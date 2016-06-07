package org.kpull.bastion.core;

import org.kpull.bastion.runner.ExecutionContext;

/**
 *
 */
@FunctionalInterface
public interface Assertions<M> {

    Assertions NO_ASSERTIONS = ((statusCode, model) -> {
    });

    @SuppressWarnings("unchecked")
    static <M> Assertions<M> noAssertions() {
        return (Assertions<M>) NO_ASSERTIONS;
    }

    void execute(int statusCode, M model);

}
