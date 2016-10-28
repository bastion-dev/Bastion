package rocks.bastion.core;

import com.google.common.base.Strings;
import rocks.bastion.core.builder.*;
import rocks.bastion.core.event.*;
import rocks.bastion.core.model.DecodingHints;
import rocks.bastion.core.model.ResponseDecoder;
import rocks.bastion.core.model.ResponseDecodersRegistrar;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

import static java.lang.String.format;

/**
 * The standard implementation of the {@link BastionBuilder}. Will keep state of the various attributes that a user supplies to Bastion
 * when specifying a request. Bastion users will not typically have to deal with this class directly but will instead interact with
 * Bastion through the various builder interfaces and the front facing {@link rocks.bastion.Bastion Bastion facade}.
 *
 * @param <MODEL> The response model type currently bound to the builder
 */
public class BastionBuilderImpl<MODEL> implements BastionBuilder<MODEL>, ResponseDecodersRegistrar, BastionEventPublisher, PostExecutionBuilder<MODEL> {

    private String message;
    private Collection<BastionListener> bastionListenerCollection;
    private Collection<ResponseDecoder> modelConverters;
    private HttpRequest request;
    private Class<MODEL> modelType;
    private boolean suppressAssertions;
    private Assertions<? super MODEL> assertions;
    private Callback<? super MODEL> callback;
    private MODEL model;
    private ModelResponse<MODEL> modelResponse;

    BastionBuilderImpl(String message, HttpRequest request) {
        Objects.requireNonNull(message);
        Objects.requireNonNull(request);
        bastionListenerCollection = new LinkedList<>();
        modelConverters = new LinkedList<>();
        this.message = message;
        this.request = request;
        modelType = null;
        suppressAssertions = false;
        assertions = Assertions.noAssertions();
        callback = Callback.noCallback();
    }

    public void addBastionListener(BastionListener newListener) {
        bastionListenerCollection.add(newListener);
    }

    /**
     * Sets whether assertions should be suppressed for this Bastion request. When assertions
     * are suppressed, Bastion will not execute whatever assertions were passed in to the {@link #withAssertions(Assertions)}
     * method.
     *
     * @param suppressAssertions {@literal true} to suppress assertions; {@literal false}, otherwise.
     */
    public void setSuppressAssertions(boolean suppressAssertions) {
        this.suppressAssertions = suppressAssertions;
    }

    @Override
    public void registerListener(BastionListener listener) {
        bastionListenerCollection.add(listener);
    }

    @Override
    public void notifyListenersCallStarted(BastionStartedEvent event) {
        Objects.requireNonNull(event);
        bastionListenerCollection.forEach(listener -> listener.callStarted(event));
    }

    @Override
    public void notifyListenersCallFailed(BastionFailureEvent event) {
        Objects.requireNonNull(event);
        bastionListenerCollection.forEach(listener -> listener.callFailed(event));
    }

    @Override
    public void notifyListenersCallError(BastionErrorEvent event) {
        Objects.requireNonNull(event);
        bastionListenerCollection.forEach(listener -> listener.callError(event));
    }

    @Override
    public void notifyListenersCallFinished(BastionFinishedEvent event) {
        Objects.requireNonNull(event);
        bastionListenerCollection.forEach(listener -> listener.callFinished(event));
    }

    @Override
    public PostExecutionBuilder<? extends MODEL> call() {
        modelResponse = null;
        try {
            notifyListenersCallStarted(new BastionStartedEvent(request));
            Response response = new RequestExecutor(request).execute();
            model = decodeModel(response);
            modelResponse = new ModelResponse<>(response, model);
            executeAssertions(modelResponse);
            executeCallback(modelResponse);
            return this;
        } catch (AssertionError e) {
            notifyListenersCallFailed(new BastionFailureEvent(request, modelResponse, e));
            return this;
        } catch (Throwable t) {
            notifyListenersCallError(new BastionErrorEvent(request, modelResponse, t));
            return this;
        } finally {
            notifyListenersCallFinished(new BastionFinishedEvent(request, modelResponse));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> AssertionsBuilder<? extends T> bind(Class<T> modelType) {
        Objects.requireNonNull(modelType);
        BastionBuilderImpl<T> castedBuilder = (BastionBuilderImpl<T>) this;
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
    public ExecuteRequestBuilder<? extends MODEL> thenDo(Callback<? super MODEL> callback) {
        Objects.requireNonNull(callback);
        this.callback = callback;
        return this;
    }

    @Override
    public MODEL getModel() {
        return model;
    }

    @Override
    public ModelResponse<? extends MODEL> getResponse() {
        return modelResponse;
    }

    @Override
    public void registerModelConverter(ResponseDecoder decoder) {
        Objects.requireNonNull(decoder);
        modelConverters.add(decoder);
    }

    private String getDescriptiveText() {
        if (Strings.isNullOrEmpty(message)) {
            return request.name();
        } else {
            return request.name() + " - " + message;
        }
    }

    private void executeCallback(ModelResponse<MODEL> modelResponse) {
        callback.execute(modelResponse.getStatusCode(), modelResponse, modelResponse.getModel());
    }

    private void executeAssertions(ModelResponse<MODEL> modelResponse) {
        if (!suppressAssertions) {
            assertions.execute(modelResponse.getStatusCode(), modelResponse, modelResponse.getModel());
        }
    }

    private MODEL decodeModel(Response response) {
        DecodingHints decodingHints = new DecodingHints(modelType);
        Object decodedResponseModel = null;
        for (ResponseDecoder converter : modelConverters) {
            decodedResponseModel = converter.decode(response, decodingHints).orElse(null);
            if (decodedResponseModel != null) {
                break;
            }
        }
        MODEL model;
        if (isModelInstanceOfRequiredType(decodedResponseModel)) {
            //noinspection unchecked
            model = (MODEL) decodedResponseModel;
        } else {
            throw new AssertionError(format("Could not parse response into model object of type %s", modelType.getName()));
        }
        return model;
    }

    private boolean isModelInstanceOfRequiredType(Object decodedResponseModel) {
        return (modelType == null) || ((decodedResponseModel != null) && modelType.isAssignableFrom(decodedResponseModel.getClass()));
    }
}