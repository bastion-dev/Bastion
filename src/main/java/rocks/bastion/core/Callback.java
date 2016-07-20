package rocks.bastion.core;

/**
 * Performs an action after an API request has occurred and passed its assertions. Users can use {@code Callback}s
 * to extract information from response recieved by an API. This information can be used for later Bastion requests.
 * <p>
 * By default, a {@link BastionBuilderImpl} builders start with the {@link Callback#noCallback() empty callback}.
 */
@FunctionalInterface
public interface Callback<M> {

    /**
     * The empty callback which does nothing.
     */
    Callback NO_OPERATION_CALLBACK = (statusCode, response, context) -> {
    };

    /**
     * Returns the empty callback which does nothing.
     *
     * @param <M> The type of model which Bastion will bind the received response to
     * @return The empty callback
     */
    @SuppressWarnings("unchecked")
    static <M> Callback<M> noCallback() {
        return (Callback<M>) NO_OPERATION_CALLBACK;
    }

    /**
     * The actual Callback action to perform after a Bastion Request completes and it passes its assertions.
     *
     * @param statusCode The <a href="http://www.restapitutorial.com/httpstatuscodes.html">HTTP status code</a> received
     *                   by the API endpoint.
     * @param response   The HTTP response information received from the API (including HTTP headers, etc.)
     * @param model      The bound model which was extracted from the received HTTP response
     */
    void execute(int statusCode, ModelResponse<? extends M> response, M model);
}
