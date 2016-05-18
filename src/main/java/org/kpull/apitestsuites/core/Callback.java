package org.kpull.apitestsuites.core;

import org.kpull.apitestsuites.runner.ExecutionContext;

/**
 * @author Francesco
 */
@FunctionalInterface
public interface Callback {

    Callback NO_OPERATION_CALLBACK = (context, environment) -> { };

    void execute(ExecutionContext context, ApiEnvironment environment);
}
