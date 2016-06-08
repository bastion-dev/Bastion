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
        return new UntypedApiRequest(message, request);
    }

    public static class UntypedApiRequest {

        private String message;
        private Request request;

        UntypedApiRequest(String message, Request request) {
            this.message = message;
            this.request = request;
        }

        public <M> ApiRequestWithTypedResponse<M> bind(Class<M> modelClass) {
            requireNonNull(modelClass);
            return new ApiRequestWithTypedResponse<>(message, request, modelClass);
        }

        public ApiRequestWithAssertions<Object> thenAssert(Assertions<Object> assertions) {
            return new ApiRequestWithAssertions<>(message, request, Object.class, assertions);
        }

        public ApiRequestWithCallback<Object> thenDo(Callback<Object> callback) {
            return new ApiRequestWithCallback<>(message, request, Object.class, Assertions.noAssertions(), Callback.noCallback());
        }

        public void call() {
            callInternal(message, request, Object.class, Assertions.noAssertions(), Callback.noCallback());
        }
    }

    public static class ApiRequestWithTypedResponse<M> {

        private String message;
        private Request request;
        private Class<M> responseType;

        ApiRequestWithTypedResponse(String message, Request request, Class<M> responseType) {
            this.message = message;
            this.request = request;
            this.responseType = responseType;
        }

        public ApiRequestWithAssertions<M> thenAssert(Assertions<M> assertions) {
            return new ApiRequestWithAssertions<>(message, request, responseType, assertions);
        }

        public ApiRequestWithCallback<M> thenDo(Callback<M> callback) {
            return new ApiRequestWithCallback<>(message, request, responseType, Assertions.noAssertions(), callback);
        }

        public void call() {
            callInternal(message, request, responseType, Assertions.noAssertions(), Callback.noCallback());
        }
    }

    public static class ApiRequestWithAssertions<M> {

        private String message;
        private Request request;
        private Class<M> responseType;
        private Assertions<M> assertions;

        public ApiRequestWithAssertions(String message, Request request, Class<M> responseType, Assertions<M> assertions) {
            this.message = message;
            this.request = request;
            this.responseType = responseType;
            this.assertions = assertions;
        }

        public ApiRequestWithCallback<M> thenDo(Callback<M> callback) {
            return new ApiRequestWithCallback<>(message, request, responseType, assertions, callback);
        }

        public void call() {
            callInternal(message, request, responseType, assertions, Callback.noCallback());
        }
    }

    public static class ApiRequestWithCallback<M> {

        private String message;
        private Request request;
        private Class<M> responseType;
        private Assertions<M> assertions;
        private Callback<M> callback;

        public ApiRequestWithCallback(String message, Request request, Class<M> responseType, Assertions<M> assertions, Callback<M> callback) {
            this.message = message;
            this.request = request;
            this.responseType = responseType;
            this.assertions = assertions;
            this.callback = callback;
        }

        public void call() {
            callInternal(message, request, responseType, assertions, callback);
        }
    }

    public static class FinishedApiResponse<M> {
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

    private static <M> void callInternal(String message, Request request, Class<M> responseType, Assertions<M> assertions, Callback<M> callback) {
        Bastion bastion = new Bastion();
        BastionListenerRegistrar.getDefaultBastionListenerRegistrar().applyListenersToBastion(bastion);

        try {
            bastion.notifyListenersCallStarted();
            Response response = new RequestExecutor(request).execute();
            M model = new Gson().fromJson(new BufferedReader((new InputStreamReader(response.getBody()))), responseType);
            assertions.execute(response.getStatusCode(), model);
            callback.execute(response.getStatusCode(), model);
        } catch (AssertionError e) {
            bastion.notifyListenersCallFailed();
        } catch (Throwable t) {
            bastion.notifyListenersCallError();
        } finally {
            bastion.notifyListenersCallFinished();
        }
    }
}