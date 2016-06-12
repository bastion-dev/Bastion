package org.kpull.bastion.core;

import org.kpull.bastion.external.Request;

public abstract class BastionFactory {

    private static BastionFactory defaultBastionFactory = null;

    public synchronized static BastionFactory getDefaultBastionFactory() {
        if (defaultBastionFactory == null) {
            setDefaultBastionFactory(new DefaultBastionFactory());
        }
        return defaultBastionFactory;
    }

    public static void setDefaultBastionFactory(BastionFactory defaultBastionFactory) {
        BastionFactory.defaultBastionFactory = defaultBastionFactory;
    }

    public Bastion<String> getBastion(String message, Request request) {
        Bastion<String> bastion = new Bastion<>(message, request);
        bastion.bind(String.class);
        prepareBastion(bastion);
        return bastion;
    }

    protected abstract void prepareBastion(Bastion<?> bastion);

}
