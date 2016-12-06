package rocks.bastion.core.event;

import rocks.bastion.core.HttpRequest;
import rocks.bastion.core.Response;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class BastionFailureEvent extends BastionEvent {

    private AssertionError assertionError;

    public BastionFailureEvent(HttpRequest request, Response response, AssertionError assertionError) {
        super(request, response);
        this.assertionError = assertionError;
    }

    public AssertionError getAssertionError() {
        return assertionError;
    }
}
