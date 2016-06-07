package org.kpull.bastion.core;

import org.kpull.bastion.external.Request;

import java.util.Collection;

public class Bastion {

    private Collection<BastionListener> bastionListenerCollection;

    public static Bastion start() {
        return new Bastion();
    }

    public ApiResponse call(final String message, final Request request) {
        bastionListenerCollection.forEach(BastionListener::callStarted);
        return new ApiResponse();
    }

    public void addBastionListener(final BastionListener newListener) {
        bastionListenerCollection.add(newListener);
    }

    public static class ApiResponse {

        private ApiResponse() { }

        public ApiResponse bindToModel(final Class<?> modelClass) {
            // something
            return this;
        }

        public void thenAssert(final Assertions assertions) {
            // something
        }

    }

}
