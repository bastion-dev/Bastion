package rocks.bastion.core;

import com.google.common.base.Strings;
import rocks.bastion.core.assertions.Assertions;
import rocks.bastion.core.builder.AssertionsBuilder;
import rocks.bastion.core.builder.BastionBuilder;
import rocks.bastion.core.builder.CallbackBuilder;
import rocks.bastion.core.builder.ExecuteRequestBuilder;
import rocks.bastion.core.event.*;
import rocks.bastion.core.model.DecodingHints;
import rocks.bastion.core.model.ModelConvertersRegistrar;
import rocks.bastion.core.model.ResponseModelConverter;
import rocks.bastion.core.request.Request;

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

    public static Bastion<String> api(String message, Request request) {
        return BastionFactory.getDefaultBastionFactory().getBastion(message, request);
    }

    public void addBastionListener(BastionListener newListener) {
        bastionListenerCollection.add(newListener);
    }

    private String getDescriptiveText() {
        if (Strings.isNullOrEmpty(message)) {
            return request.name();
        } else {
            return request.name() + " - " + message;
        }
    }

    private void callInternal() {
        Objects.requireNonNull(modelType, "Bastion instance was configured incorrectly. modelType cannot be null. Remember to bind your Bastion request to a modelType.");
        try {
            notifyListenersCallStarted(new BastionStartedEvent(getDescriptiveText()));
            Response response = new RequestExecutor(request).execute();
            MODEL model = decodeModel(response);
            ModelResponse<MODEL> modelResponse = new ModelResponse<>(response, model);
            assertions.execute(response.getStatusCode(), modelResponse, model);
            callback.execute(response.getStatusCode(), modelResponse, model);
        } catch (AssertionError e) {
            notifyListenersCallFailed(new BastionFailureEvent(getDescriptiveText(), e));
        } catch (Throwable t) {
            notifyListenersCallError(new BastionErrorEvent(getDescriptiveText(), t));
        } finally {
            notifyListenersCallFinished(new BastionFinishedEvent(getDescriptiveText()));
        }
    }

    private MODEL decodeModel(Response response) {
        MODEL model;
        DecodingHints decodingHints = new DecodingHints(modelType);
        Object decodedResponseModel = modelConverters.stream().map(converter -> converter.decode(response, decodingHints).orElse(null)).findFirst().orElse(null);
        if (modelInstanceOfRequiredType(decodedResponseModel)) {
            //noinspection unchecked
            model = (MODEL) decodedResponseModel;
        } else {
            throw new AssertionError(format("Could not parse response into model object of type %s", modelType.getName()));
        }
        return model;
    }

    private boolean modelInstanceOfRequiredType(Object decodedResponseModel) {
        return modelType.isAssignableFrom(decodedResponseModel.getClass());
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