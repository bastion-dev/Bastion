package org.kpull.bastion.core;

import org.kpull.bastion.runner.ExecutionContext;

/**
 * @author Francesco
 */
@FunctionalInterface
public interface Callback {

    Callback NO_OPERATION_CALLBACK = (statusCode, response, environment, context) -> {
    };

    void execute(int statusCode, ApiResponse response, ApiEnvironment environment, ExecutionContext context);
}
