package org.kpull.bastion.core.model;

import org.apache.commons.io.IOUtils;
import org.kpull.bastion.core.Response;

import java.io.IOException;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class StringResponseModelConverter implements ResponseModelConverter {

    @Override
    public boolean handles(Response response, Class<?> targetType) {
        return String.class.isAssignableFrom(targetType);
    }

    @Override
    public <MODEL> MODEL convert(Response response, Class<MODEL> targetType) {
        try {
            return (MODEL) IOUtils.toString(response.getBody());
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading response data", e);
        }
    }
}
