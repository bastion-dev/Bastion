package org.kpull.bastion.runner;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.kpull.bastion.core.event.*;

public class BastionRunner extends BlockJUnit4ClassRunner implements BastionListener {

    public BastionRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
        BastionListenerRegistrar.getDefaultBastionListenerRegistrar().registerListener(this);
    }

    @Override
    public Description getDescription() {
        return null;
    }

    @Override
    public void run(RunNotifier runNotifier) {

    }

    @Override
    public void callStarted(BastionStartedEvent event) {

    }

    @Override
    public void callFinished(BastionFinishedEvent event) {

    }

    @Override
    public void callFailed(BastionFailureEvent event) {

    }

    @Override
    public void callError(BastionErrorEvent event) {

    }

}
