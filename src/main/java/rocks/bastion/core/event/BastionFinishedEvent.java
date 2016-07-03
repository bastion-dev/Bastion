package rocks.bastion.core.event;

import rocks.bastion.core.Response;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class BastionFinishedEvent extends BastionEvent {
    public BastionFinishedEvent(String requestMessage, Response response) {
        super(requestMessage, response);
    }
}
