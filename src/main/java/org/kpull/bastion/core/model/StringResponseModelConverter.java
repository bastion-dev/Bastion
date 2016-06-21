package org.kpull.bastion.core.model;

import com.google.common.io.CharStreams;
import org.apache.http.Consts;
import org.apache.http.entity.ContentType;
import org.kpull.bastion.core.Response;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Optional;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class StringResponseModelConverter implements ResponseModelConverter {

    @Override
    public Optional<?> decode(Response response, DecodingHints hints) {
        try {
            Charset responseCharset = response.getContentType().map(ContentType::getCharset).orElse(Consts.ISO_8859_1);
            return Optional.of(CharStreams.toString(new InputStreamReader(response.getBody(), responseCharset)));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

}
