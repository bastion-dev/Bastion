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

    public static UntypedApiRequest api(String message, Request request) {
        Bastion bastion = new Bastion();
        BastionListenerRegistrar.getDefaultBastionListenerRegistrar().applyListenersToBastion(bastion);
        return bastion.notifyListenersAndCall(message, request);
    }

    private UntypedApiRequest notifyListenersAndCall(String message, Request request) {
        notifyListenersCallStarted();
        Response response = new RequestExecutor(request).execute();
        return new UntypedApiRequest(response);
    }

    public class UntypedApiRequest {

        private Gson gson = new Gson();
        private Response response;
        private Object model;
        private Assertions<Object> assertions;
        private Callback<Object> callback;

        UntypedApiRequest(Response response) {
            this.response = response;
        }

        public <M> ApiRequestWithTypedResponse<M> bind(Class<M> modelClass) {
            requireNonNull(modelClass);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()));
            ApiRequestWithTypedResponse<M> apiResponse = new ApiRequestWithTypedResponse<>(response);
            apiResponse.model = new Gson().fromJson(reader, modelClass);
            return apiResponse;
        }

        public ApiRequestWithAssertions<Object> thenAssert(Assertions<Object> assertions) {
            this.assertions = assertions;
            return new ApiRequestWithAssertions<>();
        }

        public ApiRequestWithCallback<Object> thenDo(Callback<Object> callback) {
            this.callback = callback;
            return new ApiRequestWithCallback<>();
        }

        public void call() {

        }
    }

    public class ApiRequestWithTypedResponse<M> {

        private Response response;

        private M model;
        private Assertions<M> assertions;
        private Callback<M> callback;

        ApiRequestWithTypedResponse(Response response) {
            this.response = response;
        }

        public ApiRequestWithAssertions<M> thenAssert(Assertions<M> assertions) {
            this.assertions = assertions;
            return new ApiRequestWithAssertions<>();
        }

        public ApiRequestWithCallback<M> thenDo(Callback<M> callback) {
            this.callback = callback;
            return new ApiRequestWithCallback<>();
        }

        public void call() {
            callInternal(null, null, null, null, null);
        }
    }

    public class ApiRequestWithAssertions<M> {

        private Callback<M> callback;

        public ApiRequestWithCallback<M> thenDo(Callback<M> callback) {
            this.callback = callback;
            return new ApiRequestWithCallback<>();
        }

        public void call() {

        }
    }

    public class ApiRequestWithCallback<M> {
        public void call() {

        }
    }

    public class FinishedApiResponse<M> {
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

    public void addBastionListener(BastionListener newListener) {
        bastionListenerCollection.add(newListener);
    }

    private <M> void callInternal(String message, Request request, M model, Assertions<M> assertions, Callback<M> callback) {
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
}