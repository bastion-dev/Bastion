package rocks.bastion.core.builder;

import rocks.bastion.core.Callback;

/**
 * Defines the operations which a user can perform on a Bastion builder before any {@link Callback callbacks} have been added to the test.
 * At this point, a user may perform one of the following:
 * <ul>
 * <li>{@link #thenDo(Callback)}: Specify a callback function to execute when the response is received and it passes its assertions. We
 * recommend supplying the {@link Callback} as a lambda function.</li>
 * <li>{@link #call()}: Starts the Bastion test by executing the HTTP request.</li>
 * </ul>
 * <br>
 * After using the {@linkplain #call()} method, the user may obtain the response, for further use in the ongoing test, using
 * methods defined in the {@link PostExecutionBuilder} interface.
 *
 * @param <MODEL> The model type currently bound to this Bastion builder
 */
public interface CallbackBuilder<MODEL> extends ExecuteRequestBuilder<MODEL> {

    /**
     * Attach a callback function to this Bastion test. The callback function will be executed after a response has been
     * received, it has been decoded into a model object and any assertions have passed.
     * <br><br>
     * If this method is not called on the current Bastion builder, then a {@link Callback#noCallback() no-operations callback
     * function} which does nothing will be set by default.
     *
     * @param callback A callback function to execute after this Bastion test's assertions have passed
     * @return A fluent-builder which will allow you to execute the request and then retrieve the response
     */
    ExecuteRequestBuilder<? extends MODEL> thenDo(Callback<? super MODEL> callback);

}
