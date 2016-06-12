package org.kpull.bastion.runner;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.kpull.bastion.core.Bastion;
import org.kpull.bastion.core.BastionFactory;
import org.kpull.bastion.core.event.*;
import org.kpull.bastion.core.model.GsonResponseModelConverter;
import org.kpull.bastion.core.model.StringResponseModelConverter;

import java.util.concurrent.ConcurrentHashMap;

public class BastionRunner extends BlockJUnit4ClassRunner implements BastionListener {

    private ConcurrentHashMap<FrameworkMethod, Description> methodDescriptions = new ConcurrentHashMap<FrameworkMethod, Description>();

    private Description runningTestCase;

    private Description runningBastionRequest;
    private RunNotifier currentNotifier;

    public BastionRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
        BastionFactory.setDefaultBastionFactory(new BastionFactory() {
            @Override
            protected void prepareBastion(Bastion<?> bastion) {
                bastion.registerModelConverter(new StringResponseModelConverter());
                bastion.registerModelConverter(new GsonResponseModelConverter());
                bastion.registerListener(BastionRunner.this);
            }
        });
    }

    @Override
    protected Description describeChild(FrameworkMethod method) {
        Description description = methodDescriptions.get(method);
        if (description == null) {
            description = Description.createSuiteDescription(testName(method), method.getAnnotations());
            methodDescriptions.putIfAbsent(method, description);
        }
        return description;
    }

    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        runningTestCase = describeChild(method);
        currentNotifier = notifier;
        super.runChild(method, notifier);
    }

    @Override
    public void callStarted(BastionStartedEvent event) {
        runningBastionRequest = Description.createTestDescription(runningTestCase.getDisplayName(), event.getRequestMessage());
        runningTestCase.addChild(runningBastionRequest);
        currentNotifier.fireTestStarted(runningBastionRequest);
    }

    @Override
    public void callFinished(BastionFinishedEvent event) {
        currentNotifier.fireTestFinished(runningBastionRequest);
    }

    @Override
    public void callFailed(BastionFailureEvent event) {
        currentNotifier.fireTestFailure(new Failure(runningBastionRequest, event.getAssertionError()));
        throw event.getAssertionError();
    }

    @Override
    public void callError(BastionErrorEvent event) {
        currentNotifier.fireTestFailure(new Failure(runningBastionRequest, event.getThrowable()));
        if (event.getThrowable() instanceof RuntimeException) {
            throw (RuntimeException) event.getThrowable();
        } else if (event.getThrowable() instanceof Error) {
            throw (Error) event.getThrowable();
        } else {
            throw new RuntimeException(event.getThrowable());
        }
    }

}
