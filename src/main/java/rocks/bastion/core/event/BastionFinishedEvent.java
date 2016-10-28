package rocks.bastion.core.event;

import rocks.bastion.core.HttpRequest;
import rocks.bastion.core.Response;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class BastionFinishedEvent extends BastionEvent {
    public BastionFinishedEvent(HttpRequest request, Response response) {
        super(request, response);
    }
}
