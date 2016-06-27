package rocks.bastion.core.model;

/**
 * Provides hints to the {@link ResponseModelConverter} about how to interpret the given HTTP response. Typically this
 * object will contain the type of model that was requested by the user so that a {@link ResponseModelConverter} can
 * decode the response into a specific model as requested by the user.
 */
public class DecodingHints {

    private Class<?> modelType;

    public DecodingHints(Class<?> modelType) {
        this.modelType = modelType;
    }

    public Class<?> getModelType() {
        return modelType;
    }
}
