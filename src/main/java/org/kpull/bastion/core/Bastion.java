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

    public static UntypedApiResponse api(final String message, final Request request) {
        return BastionFactory.getDefaultBastionFactory().create().notifyListenersAndCall(message, request);
    }

    private UntypedApiResponse notifyListenersAndCall(final String message, final Request request) {
        notifyListenersCallStarted();
        Response response = new RequestExecutor(request).execute();
        return new UntypedApiResponse(response);
    }

    public class UntypedApiResponse {

        private Gson gson = new Gson();

        UntypedApiResponse(Response response) {
            this.response = response;
        }

        private Response response;

        private Object model;
        private Assertions<Object> assertions;
        private Callback<Object> callback;

        public <M> BoundApiResponse<M> bind(Class<M> modelClass) {
            requireNonNull(modelClass);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()));
            BoundApiResponse<M> apiResponse = new BoundApiResponse<>(response);
            apiResponse.model = gson.fromJson(reader, modelClass);
            return apiResponse;
        }

        public AssertedApiResponse<Object> thenAssert(Assertions<Object> assertions) {
            this.assertions = assertions;
        }

        public CallbackedApiResponse<Object> thenDo(Callback<Object> callback) {
            this.callback = callback;
        }

        public FinishedApiResponse<Object> call() {

        }
    }

    public class BoundApiResponse<T> {

        private Response response;

        private T model;
        private Assertions<T> assertions;
        BoundApiResponse(Response response) {
            this.response = response;
        }

        public AssertedApiResponse<T> thenAssert(Assertions<T> assertions) {
            this.assertions = assertions;
        }

        public CallbackedApiResponse<T> thenDo(Callback<T> callback) {
            this.callback = callback;
        }

        public FinishedApiResponse<T> call() {

        }
    }

    public class AssertedApiResponse<T> {
        public CallbackedApiResponse<T> thenDo(Callback<T> callback) {
            this.callback = callback;
        }

        public FinishedApiResponse<T> call() {

        }
    }

    public class CallbackedApiResponse<T> {
        public FinishedApiResponse<T> call() {

        }
    }

    public class FinishedApiResponse<T> {

    }

    private void notifyListenersCallStarted() {
        bastionListenerCollection.forEach(BastionListener::callStarted);
    }

    private void notifyListenersCallFailed() {
        bastionListenerCollection.forEach(BastionListener::callFailed);
    }

    private void notifyListenersCallError() {
        bastionListenerCollection.forEach(BastionListener::callError);
    }

    private void notifyListenersCallFinished() {
        bastionListenerCollection.forEach(BastionListener::callFinished);
    }
}