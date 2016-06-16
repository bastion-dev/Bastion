package org.kpull.bastion.core;

import org.kpull.bastion.core.event.*;
import org.kpull.bastion.core.model.GsonResponseModelConverter;
import org.kpull.bastion.core.model.StringResponseModelConverter;

/**
 * The default implementation of a {@link BastionFactory} which registers a basic set of {@link org.kpull.bastion.core.model.ResponseModelConverter model converters}. Also registers
 * an event listener to Bastion which will cause exceptions and errors thrown during a Bastion request to propagate up the
 * frame stack.
 * <br/><br/>
 * The set of model converters which are registered by this factory are listed below: <ul>
 *     <li>{@link StringResponseModelConverter} - Binds the response to a {@link String} model by reading the content
 *     inside the HTTP response.
 *     <li>{@link GsonResponseModelConverter} - Binds an HTTP response with content-type {@code application/json} to
 *     a Java-based object using <a href="https://github.com/google/gson">Gson</a>.</li>
 * </ul>
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
