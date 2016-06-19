package org.kpull.bastion.core.event;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class BastionFinishedEvent extends BastionEvent {
    public BastionFinishedEvent(String requestMessage) {
        super(requestMessage);
    }
}
