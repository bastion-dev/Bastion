package org.kpull.bastion.core.model;

import org.kpull.bastion.core.Response;

public interface ResponseModelConverter {

    boolean handles(Response response, Class<?> targetType);

    <MODEL> MODEL convert(Response response, Class<MODEL> targetType);

}
