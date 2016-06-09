package org.kpull.bastion.core;

/**
 *
 */
@FunctionalInterface
public interface Assertions<M> {

    Assertions NO_ASSERTIONS = ((statusCode, response, model) -> {
    });

    @SuppressWarnings("unchecked")
    static <M> Assertions<M> noAssertions() {
        return (Assertions<M>) NO_ASSERTIONS;
    }

    void execute(int statusCode, ModelResponse<? extends M> response, M model);

}
