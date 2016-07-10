package rocks.bastion.core.model;

import rocks.bastion.core.Response;

import java.util.Optional;

/**
 * Interprets and decodes an HTTP response into a model object. Bastion will ask ResponseModelConverters to decode the
 * HTTP response if possible. If the response model converter is unable to decode the HTTP response into a Java object,
 * then it should return an {@link Optional#empty() empty Optional}.
 * <br><br>
 * Together, a list of ResponseModelConverters will form a strategy for turning an arbitrary HTTP response to a usable
 * Java object usable in Bastion tests and assertions.
 */
public interface ResponseDecoder {

    /**
     * Attempts to decode the given HTTP response to a Java object. If this model converter is unable to decode the
     * HTTP response, then it should return an {@link Optional#empty() empty Optional}. Otherwise, it should return
     * an {@link Optional#of(Object) Optional container containing the decoded object}.
     *
     * @param response The HTTP response for the converter to decode
     * @param hints    Additional hints which can be used for decoding the provided response
     * @return An empty {@link Optional} if this converter cannot decode the HTTP response. An {@link Optional} containing
     * the decoded object, otherwise.
     */
    Optional<?> decode(Response response, DecodingHints hints);
}
