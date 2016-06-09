package org.kpull.bastion.core;

/**
 *
 */
@FunctionalInterface
public interface Callback<M> {

    Callback NO_OPERATION_CALLBACK = (statusCode, response, context) -> {
    };

    @SuppressWarnings("unchecked")
    static <M> Callback<M> noCallback() {
        return (Callback<M>) NO_OPERATION_CALLBACK;
    }

    void execute(int statusCode, ModelResponse<? extends M> response, M model);
}
