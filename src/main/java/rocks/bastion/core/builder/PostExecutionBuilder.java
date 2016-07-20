package rocks.bastion.core.builder;

import rocks.bastion.core.ModelResponse;

/**
 * Specifies the operations available on a Bastion fluent-builder after the test has been executed with {@link ExecuteRequestBuilder#call()} and
 * a response has been obtained. At this point, Bastion will have obtained a response and decoded the response body into
 * some sort of Java object known as the model.
 * <br><br>
 * If a type has been bound to this builder using {@link BindBuilder#bind(Class)}, then the model given by {@link #getModel()}
 * will be of the specified type. Otherwise, the type of model will be determined by Bastion itself depending on the content-type
 * of the response.
 *
 * @param <MODEL> The type of model bound to this builder
 */
public interface PostExecutionBuilder<MODEL> {

    /**
     * Returns the model object that was decoded from the received HTTP response. If a type has been bound to this builder
     * using {@link BindBuilder#bind(Class)}, then the model given by this method
     * will be of the specified type. Otherwise, the type of model will be determined by Bastion itself depending on the content-type
     * of the response.
     *
     * @return The decoded model object
     */
    MODEL getModel();

    /**
     * Returns the complete HTTP response as received. This response will contain the HTTP status code, the content-type
     * and any HTTP headers. The response will also contain the raw response content as well as the decoded model object
     * (which you can get directly using {@link #getModel()}).
     *
     * @return The full HTTP response object
     */
    ModelResponse<? extends MODEL> getResponse();

}
