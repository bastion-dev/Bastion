package org.kpull.bastion.core;

import org.kpull.bastion.runner.ExecutionContext;

/**
 * @author Francesco
 */
@FunctionalInterface
public interface Callback {

    Callback NO_OPERATION_CALLBACK = (context, environment) -> { };

    void execute(ExecutionContext context, ApiEnvironment environment);
}
