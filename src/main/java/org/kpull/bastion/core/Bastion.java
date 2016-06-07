package org.kpull.bastion.core;

import com.google.gson.Gson;
import org.kpull.bastion.external.Request;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static java.util.Objects.requireNonNull;

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

    public static class ApiResponse<T> {

        private Gson gson = new Gson();

        private ApiResponse() { }

        private T model;
        private Response response;

        public ApiResponse bindToModel(Class<T> modelClass) {
            requireNonNull(modelClass);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()));
            model = gson.fromJson(reader, modelClass);
            return this;
        }

        public void thenAssert(final Assertions assertions) {
            // something
        }

    }

}
