package org.kpull.bastion.core.model;

import com.google.gson.Gson;
import org.apache.http.Consts;
import org.apache.http.entity.ContentType;
import org.kpull.bastion.core.Response;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Optional;

public class GsonResponseModelConverter implements ResponseModelConverter {

    @Override
    public Optional<?> decode(Response response, DecodingHints hints) {
        ContentType responseContentType = response.getContentType().orElse(ContentType.DEFAULT_TEXT);
        if (!responseContentType.getMimeType().equals(ContentType.APPLICATION_JSON.getMimeType())) {
            return Optional.empty();
        }
        Charset responseCharset = responseContentType.getCharset() != null ? responseContentType.getCharset() : Consts.ISO_8859_1;
        Gson gson = new Gson();
        return Optional.ofNullable(gson.fromJson(new InputStreamReader(response.getBody(), responseCharset), hints.getModelType()));
    }
}
