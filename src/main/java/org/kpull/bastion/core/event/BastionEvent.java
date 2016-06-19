package org.kpull.bastion.core.event;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class BastionEvent {

    private String requestMessage;

    public BastionEvent(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public String getRequestMessage() {
        return requestMessage;
    }
}
