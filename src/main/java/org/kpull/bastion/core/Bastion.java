package org.kpull.bastion.core;

import org.kpull.bastion.core.builder.AssertionsBuilder;
import org.kpull.bastion.core.builder.BastionBuilder;
import org.kpull.bastion.core.builder.CallbackBuilder;
import org.kpull.bastion.core.builder.ExecuteRequestBuilder;
import org.kpull.bastion.core.event.*;
import org.kpull.bastion.core.model.ModelConvertersRegistrar;
import org.kpull.bastion.core.model.ResponseModelConverter;
import org.kpull.bastion.external.Request;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

import static java.lang.String.format;

public class Bastion<MODEL> implements BastionBuilder<MODEL>, ModelConvertersRegistrar, BastionEventPublisher {

    private String message;
    private Collection<BastionListener> bastionListenerCollection;
    private Collection<ResponseModelConverter> modelConverters;
    private Request request;
    private Class<MODEL> modelType;
    private Assertions<? super MODEL> assertions;
    private Callback<? super MODEL> callback;

    Bastion(String message, Request request) {
        Objects.requireNonNull(message);
        Objects.requireNonNull(request);
        bastionListenerCollection = new LinkedList<>();
        modelConverters = new LinkedList<>();
        this.message = message;
        this.request = request;
        this.modelType = null;
        this.assertions = Assertions.noAssertions();
        this.callback = Callback.noCallback();
    }

    public static BastionBuilder<Object> api(String message, Request request) {
        return BastionFactory.getDefaultBastionFactory().getBastion(message, request);
    }

    public void addBastionListener(BastionListener newListener) {
        bastionListenerCollection.add(newListener);
    }

    private void callInternal() {
        try {
            notifyListenersCallStarted(new BastionStartedEvent());
            Response response = new RequestExecutor(request).execute();
            MODEL model;
            if (modelType != null) {
                model = modelConverters.stream().filter(converter -> converter.handles(response, modelType))
                        .map(converter -> converter.convert(response, modelType))
                        .findFirst()
                        .orElseThrow(() -> new AssertionError(format("Could not parse response into model object of type %s", modelType.getName())));
            } else {
                model = null;
            }
            assertions.execute(response.getStatusCode(), model);
            callback.execute(response.getStatusCode(), model);
        } catch (AssertionError e) {
            notifyListenersCallFailed(new BastionFailureEvent(e));
        } catch (Throwable t) {
            notifyListenersCallError(new BastionErrorEvent(t));
        } finally {
            notifyListenersCallFinished(new BastionFinishedEvent());
        }
    }

    @Override
    public void registerListener(BastionListener listener) {
        bastionListenerCollection.add(listener);
    }

    @Override
    public void notifyListenersCallStarted(BastionStartedEvent event) {
        Objects.requireNonNull(event);
        bastionListenerCollection.forEach((listener) -> listener.callStarted(event));
    }

    @Override
    public void notifyListenersCallFailed(BastionFailureEvent event) {
        Objects.requireNonNull(event);
        bastionListenerCollection.forEach((listener) -> listener.callFailed(event));
    }

    @Override
    public void notifyListenersCallError(BastionErrorEvent event) {
        Objects.requireNonNull(event);
        bastionListenerCollection.forEach((listener) -> listener.callError(event));
    }

    @Override
    public void notifyListenersCallFinished(BastionFinishedEvent event) {
        Objects.requireNonNull(event);
        bastionListenerCollection.forEach((listener) -> listener.callFinished(event));
    }

    @Override
    public void call() {
        callInternal();
    }

    @Override
    public Response getResponse() {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> AssertionsBuilder<? extends T> bind(Class<T> modelType) {
        Objects.requireNonNull(modelType);
        Bastion<T> castedBuilder = (Bastion<T>) this;
        castedBuilder.modelType = modelType;
        return castedBuilder;
    }

    @Override
    public CallbackBuilder<? extends MODEL> withAssertions(Assertions<? super MODEL> assertions) {
        Objects.requireNonNull(assertions);
        this.assertions = assertions;
        return this;
    }

    @Override
    public ExecuteRequestBuilder thenDo(Callback<? super MODEL> callback) {
        Objects.requireNonNull(callback);
        this.callback = callback;
        return this;
    }

    @Override
    public void registerModelConverter(ResponseModelConverter converter) {
        Objects.requireNonNull(converter);
        modelConverters.add(converter);
    }
}