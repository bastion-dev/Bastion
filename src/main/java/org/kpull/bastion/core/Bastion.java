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

    private <M> void callInternal(final String message, final Request request, M model, Assertions<M> assertions, Callback<M> callback) {
        try {
            notifyListenersCallStarted();
            Response response = new RequestExecutor(request).execute();
            assertions.execute(response.getStatusCode(), model);
            callback.execute(response.getStatusCode(), model);
        } catch (AssertionError e) {
            notifyListenersCallFailed();
        } catch (Throwable t) {
            notifyListenersCallError();
        } finally {
            notifyListenersCallFinished();
        }
    }

    private UntypedApiResponse notifyListenersAndCall(final String message, final Request request) {
        notifyListenersCallStarted();
        Response response = new RequestExecutor(request).execute();
        return new UntypedApiResponse(response);
    }

    public class UntypedApiResponse {

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
            apiResponse.model = new Gson().fromJson(reader, modelClass);
            return apiResponse;
        }

        public AssertedApiResponse<Object> thenAssert(Assertions<Object> assertions) {
            this.assertions = assertions;
        }

        public CallbackedApiResponse<Object> thenDo(Callback<Object> callback) {
            this.callback = callback;
        }

        public void call() {

        }
    }

    public class BoundApiResponse<M> {

        private Response response;

        private M model;
        private Assertions<M> assertions;

        BoundApiResponse(Response response) {
            this.response = response;
        }

        public AssertedApiResponse<M> thenAssert(Assertions<M> assertions) {
            this.assertions = assertions;
        }

        public CallbackedApiResponse<M> thenDo(Callback<M> callback) {
            this.callback = callback;
        }

        public void call() {
            callInternal();
        }
    }

    public class AssertedApiResponse<M> {
        public CallbackedApiResponse<M> thenDo(Callback<M> callback) {
            this.callback = callback;
        }

        public void call() {

        }
    }

    public class CallbackedApiResponse<M> {
        public void call() {

        }
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