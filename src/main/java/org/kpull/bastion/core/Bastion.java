package org.kpull.bastion.core;

import com.google.gson.Gson;
import org.kpull.bastion.external.Request;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

public class Bastion {

    private Collection<BastionListener> bastionListenerCollection;

    Bastion() {
        bastionListenerCollection = new ArrayList<>();
    }

    public void addBastionListener(final BastionListener newListener) {
        bastionListenerCollection.add(newListener);
    }

    public static UntypedApiResponse call(final String message, final Request request) {
        return BastionFactory.getDefaultBastionFactory().create().notifyListenersAndCall(message, request);
    }

    private UntypedApiResponse notifyListenersAndCall(final String message, final Request request) {
        bastionListenerCollection.forEach(BastionListener::callStarted);
        Response response = new RequestExecutor(request).execute();
        return new UntypedApiResponse(response);
    }

    private class UntypedApiResponse {

        private Gson gson = new Gson();

        UntypedApiResponse(Response response) {
            this.response = response;
        }

        private Response response;
        private Object model;

        public <M> TypedApiResponse<M> bind(Class<M> modelClass) {
            requireNonNull(modelClass);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()));
            TypedApiResponse<M> apiResponse = new TypedApiResponse<>(response);
            apiResponse.model = gson.fromJson(reader, modelClass);
            return apiResponse;
        }

        public void thenAssert(Assertions<Object> assertions) {
            assertions.assertions(response.getStatusCode(), model);
        }
    }

    private class TypedApiResponse<T> {

        private Response response;
        private T model;

        TypedApiResponse(Response response) {
            this.response = response;
        }

        public void thenAssert(Assertions<T> assertions) {
            assertions.assertions(response.getStatusCode(), model);
        }
    }
}