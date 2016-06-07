package org.kpull.bastion.core;

import org.kpull.bastion.runner.ExecutionContext;

/**
 *
 */
@FunctionalInterface
public interface Callback<M> {

    Callback NO_OPERATION_CALLBACK = (statusCode, context) -> {
    };

    @SuppressWarnings("unchecked")
    static <M> Callback<M> noCallback() {
        return (Callback<M>) NO_OPERATION_CALLBACK;
    }

    void execute(int statusCode, M model);
}
