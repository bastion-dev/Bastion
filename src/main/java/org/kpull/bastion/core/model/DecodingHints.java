package org.kpull.bastion.core.model;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
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
