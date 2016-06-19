package org.kpull.bastion.core.event;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class BastionStartedEvent extends BastionEvent {

    public BastionStartedEvent(String requestMessage) {
        super(requestMessage);
    }
}
