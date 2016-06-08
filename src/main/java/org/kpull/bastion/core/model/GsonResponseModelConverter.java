package org.kpull.bastion.core.model;

import com.google.gson.Gson;
import org.apache.http.entity.ContentType;
import org.kpull.bastion.core.Response;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Objects;

public class GsonResponseModelConverter implements ResponseModelConverter {

    @Override
    public <MODEL> MODEL convert(Response response, Class<MODEL> targetType) {
        Objects.requireNonNull(response);
        Objects.requireNonNull(targetType);
        Charset charset = response.getContentType().orElse(ContentType.APPLICATION_JSON).getCharset();
        if (charset == null) {
            charset = Charset.defaultCharset();
        }
        Gson gson = new Gson();
        return gson.fromJson(new InputStreamReader(response.getBody(), charset), targetType);
    }

    @Override
    public boolean handles(Response response, Class<?> targetType) {
        return response.getContentType().isPresent() && response.getContentType().get().getMimeType().equals(ContentType.APPLICATION_JSON.getMimeType());
    }
}
