package rocks.bastion.core.model;

import rocks.bastion.core.Response;

import java.util.Optional;

/**
 * Interprets and decodes an HTTP response into a model object. Bastion will ask {@linkplain ResponseDecoder}s to decode the
 * HTTP response if possible. A typical implementation will first look at the response's {@code Content-type} header and attempt
 * to generate some sort of object. If the decoder is unable to decode the HTTP response into a Java object
 * (because it cannot interpret the given {@code Content-type}, for example), then then it should return an {@link Optional#empty()
 * empty Optional}.
 * <br><br>
 * Once registered with Bastion, the {@linkplain ResponseDecoder}s will form a strategy for turning an arbitrary HTTP response
 * to a usable Java object available in Bastion tests and assertions.
 */
public interface ResponseDecoder {

    /**
     * Attempts to decode the given HTTP response to a Java object. If this decoder is unable to decode the
     * HTTP response, then it should return an {@link Optional#empty() empty Optional}. Otherwise, it should return
     * an {@link Optional#of(Object) Optional container containing the decoded object}.
     * <p>
     * A user might have supplied a <i>target</i> object type it wants the decoded information bound to. In this case,
     * the target model type to bind to will be given in the {@code hints} parameter, accessible using the
     * {@link DecodingHints#getModelType()} method.
     *
     * @param response The HTTP response for the converter to decode
     * @param hints    Additional hints which can be used for decoding the provided response
     * @return An empty {@link Optional} if this converter cannot decode the HTTP response. An {@link Optional} containing
     * the decoded object, otherwise.
     */
    Optional<?> decode(Response response, DecodingHints hints);
}
