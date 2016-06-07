package org.kpull.bastion.core;

import org.kpull.bastion.external.Request;

public class Bastion {

    public static Bastion start() {
        return new Bastion();
    }

    public static ApiResponse call(final String message, final Request request) {
        return new ApiResponse();
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
