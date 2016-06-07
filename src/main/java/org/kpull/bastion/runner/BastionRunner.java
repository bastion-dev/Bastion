package org.kpull.bastion.runner;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.kpull.bastion.core.BastionFactory;
import org.kpull.bastion.core.BastionListener;

public class BastionRunner extends BlockJUnit4ClassRunner implements BastionListener {

    public BastionRunner(final Class<?> testClass) throws InitializationError {
        super(testClass);

        final BastionFactory bastionFactory = new BastionFactory();
        BastionFactory.setDefaultBastionFactory(bastionFactory);
        bastionFactory.create().addBastionListener(this);
    }

    @Override
    public Description getDescription() {
        return null;
    }

    @Override
    public void run(final RunNotifier runNotifier) {

    }

    @Override
    public void callStarted() {

    }

    @Override
    public void callFinished() {

    }

    @Override
    public void callFailed() {

    }

}
