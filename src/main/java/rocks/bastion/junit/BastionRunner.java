package rocks.bastion.junit;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import rocks.bastion.core.BastionFactory;
import rocks.bastion.core.DefaultBastionFactory;
import rocks.bastion.core.event.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BastionRunner extends BlockJUnit4ClassRunner implements BastionListener {

    private Map<FrameworkMethod, Description> methodDescriptions = new ConcurrentHashMap<>();

    private Description runningTestCase;

    private Description runningBastionRequest;
    private RunNotifier currentNotifier;

    public BastionRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    public void callStarted(BastionStartedEvent event) {
        runningBastionRequest = Description.createTestDescription(runningTestCase.getDisplayName(), event.getRequest().name());
        runningTestCase.addChild(runningBastionRequest);
        currentNotifier.fireTestStarted(runningBastionRequest);
    }

    @Override
    public void callFinished(BastionFinishedEvent event) {
        currentNotifier.fireTestFinished(runningBastionRequest);
    }

    @Override
    public void callFailed(BastionFailureEvent event) {
        new EventLogging(event).logResponseAndRequest();
        currentNotifier.fireTestFailure(new Failure(runningBastionRequest, event.getAssertionError()));
        throw event.getAssertionError();
    }

    @Override
    public void callError(BastionErrorEvent event) {
        new EventLogging(event).logResponseAndRequest();
        Throwable throwable = event.getThrowable();
        currentNotifier.fireTestFailure(new Failure(runningBastionRequest, throwable));
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException) throwable;
        } else if (throwable instanceof Error) {
            throw (Error) throwable;
        } else {
            throw new RuntimeException(throwable);
        }
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
        BastionFactory.setDefaultBastionFactory(new DefaultBastionFactory());
        super.runChild(method, notifier);
    }

}
