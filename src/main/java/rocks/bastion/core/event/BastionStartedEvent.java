package rocks.bastion.core.event;

import rocks.bastion.core.HttpRequest;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class BastionStartedEvent extends BastionEvent {

    public BastionStartedEvent(HttpRequest request) {
        super(request, null);
    }
}
