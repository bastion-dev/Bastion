package org.kpull.bastion.core;

import org.kpull.bastion.external.Request;

public class RequestExecutor {

    private Request requestToExecute;

    public RequestExecutor(Request requestToExecute) {
        this.requestToExecute = requestToExecute;
    }

    public Response execute() {
        throw new UnsupportedOperationException("NYI");
    }

}
