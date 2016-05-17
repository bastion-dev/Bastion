package org.kpull.apitestsuites.core;

import org.kpull.apitestsuites.runner.ExecutionContext;

/**
 * @author Francesco
 */
@FunctionalInterface
public interface PostCallExecution {

    void execute(ExecutionContext context, ApiEnvironment environment);
}
