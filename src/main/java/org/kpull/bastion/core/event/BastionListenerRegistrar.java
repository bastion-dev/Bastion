package org.kpull.bastion.core.event;

import org.kpull.bastion.core.Bastion;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

public class BastionListenerRegistrar {

    private static BastionListenerRegistrar defaultBastionListenerRegistrar = new BastionListenerRegistrar();
    public Collection<BastionListener> listeners;

    public BastionListenerRegistrar() {
        listeners = new LinkedList<>();
    }

    public static BastionListenerRegistrar getDefaultBastionListenerRegistrar() {
        return defaultBastionListenerRegistrar;
    }

    public static void setDefaultBastionListenerRegistrar(BastionListenerRegistrar defaultBastionListenerRegistrar) {
        Objects.requireNonNull(defaultBastionListenerRegistrar);
        BastionListenerRegistrar.defaultBastionListenerRegistrar = defaultBastionListenerRegistrar;
    }

    public void registerListener(BastionListener listener) {
        Objects.requireNonNull(listener);
        listeners.add(listener);
    }

    public void applyListenersToBastion(Bastion bastion) {
        Objects.requireNonNull(bastion);
        listeners.forEach(bastion::addBastionListener);
    }

}
