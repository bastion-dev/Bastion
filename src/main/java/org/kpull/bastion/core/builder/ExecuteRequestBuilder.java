package org.kpull.bastion.core.builder;

import org.kpull.bastion.core.Response;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public interface ExecuteRequestBuilder {

    void call();

    Response getResponse();

}
