package rocks.bastion.core.model;

import com.google.common.io.CharStreams;
import org.apache.http.Consts;
import org.apache.http.entity.ContentType;
import rocks.bastion.core.Response;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Optional;

/**
 * A {@link ResponseDecoder} which will take the HTTP response content-body and put it in to a {@link String}. This should be
 * the last decoder which gets executed in the decoder chain. If none of the other decoders Bastion execute could decode
 * the HTTP response body, then this final decoder will just take the content body data and initialise a string using it.
 * This ensures that all Bastion tests contain some sort of model object decoded from the response.
 */
public class StringResponseDecoder implements ResponseDecoder {

    @Override
    public Optional<?> decode(Response response, DecodingHints hints) {
        try {
            Charset responseCharset = response.getContentType().map(ContentType::getCharset).orElse(Consts.ISO_8859_1);
            return Optional.of(CharStreams.toString(new InputStreamReader(response.getBody(), responseCharset)));
        } catch (IOException ignored) {
            return Optional.empty();
        }
    }

}
