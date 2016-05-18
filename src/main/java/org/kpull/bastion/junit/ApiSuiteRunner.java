package org.kpull.bastion.junit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.kpull.bastion.core.ApiCall;
import org.kpull.bastion.core.ApiEnvironment;
import org.kpull.bastion.core.ApiSuite;
import org.kpull.bastion.runner.ApiCallExecutor;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class ApiSuiteRunner extends ParentRunner<ApiSuite> {

    public ApiSuiteRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected List<ApiSuite> getChildren() {
        try {
            Object test = getTestClass().getOnlyConstructor().newInstance();
            return getTestClass().getAnnotatedMethodValues(test, org.kpull.bastion.junit.ApiSuite.class, ApiSuite.class);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("Problem computing ApiSuite methods in test class", e);
        }
    }

    @Override
    protected Description describeChild(ApiSuite suiteToDescribe) {
        String suiteName = suiteToDescribe.getName();
        Description suiteDescription = Description.createSuiteDescription(suiteName);
        suiteToDescribe.getApiCalls().forEach(apiCall -> suiteDescription.addChild(describeApiCall(suiteName, apiCall)));
        return suiteDescription;
    }

    private Description describeApiCall(String suiteName, ApiCall apiCall) {
        return Description.createTestDescription(suiteName, apiCall.getName());
    }

    @Override
    protected void runChild(ApiSuite child, RunNotifier notifier) {
        ApiEnvironment environment = child.getEnvironment();
        List<ApiCall> apiCalls = child.getApiCalls();
        apiCalls.forEach(apiCall -> {
            Description apiCallDescription = describeApiCall(child.getName(), apiCall);
            notifier.fireTestStarted(apiCallDescription);
            try {
                ApiCallExecutor executor = new ApiCallExecutor(environment, apiCall, new ObjectMapper());
                executor.execute();
            } catch (AssumptionViolatedException e) {
                notifier.fireTestAssumptionFailed(new Failure(apiCallDescription, e));
            } catch (Throwable e) {
                notifier.fireTestFailure(new Failure(apiCallDescription, e));
            } finally {
                notifier.fireTestFinished(apiCallDescription);
            }
        });
    }
}
