package org.kpull.apitestsuites.core;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
@FunctionalInterface
public interface Assertions<M extends Object> {

    void assertions(ApiEnvironment apiEnvironment, ApiCall apiCall, int statusCode, M model);

}
