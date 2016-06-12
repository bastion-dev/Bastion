package org.kpull.bastion.core;

import org.kpull.bastion.core.event.*;
import org.kpull.bastion.core.model.GsonResponseModelConverter;
import org.kpull.bastion.core.model.StringResponseModelConverter;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class DefaultBastionFactory extends BastionFactory implements BastionListener {

    @Override
    protected void prepareBastion(Bastion<?> bastion) {
        bastion.registerModelConverter(new StringResponseModelConverter());
        bastion.registerModelConverter(new GsonResponseModelConverter());
        bastion.registerListener(this);
    }

    @Override
    public void callStarted(BastionStartedEvent event) {

    }

    @Override
    public void callFinished(BastionFinishedEvent event) {

    }

    @Override
    public void callFailed(BastionFailureEvent event) {
        throw event.getAssertionError();
    }

    @Override
    public void callError(BastionErrorEvent event) {
        if (event.getThrowable() instanceof RuntimeException) {
            throw (RuntimeException) event.getThrowable();
        } else if (event.getThrowable() instanceof Error) {
            throw (Error) event.getThrowable();
        } else {
            throw new RuntimeException(event.getThrowable());
        }
    }
}
