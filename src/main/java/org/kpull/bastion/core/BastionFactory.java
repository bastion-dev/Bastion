package org.kpull.bastion.core;

public class BastionFactory {

    private static BastionFactory defaultBastionFactory = new BastionFactory();

    public static void setDefaultBastionFactory(final BastionFactory defaultBastionFactory) {
        BastionFactory.defaultBastionFactory = defaultBastionFactory;
    }

    public static BastionFactory getDefaultBastionFactory() {
        return defaultBastionFactory;
    }

    public Bastion create() {
        return new Bastion();
    }

}
