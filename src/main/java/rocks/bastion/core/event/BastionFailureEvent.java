package rocks.bastion.core.event;

import rocks.bastion.core.Response;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class BastionFailureEvent extends BastionEvent {

    private AssertionError assertionError;

    public BastionFailureEvent(String requestMessage, Response response, AssertionError assertionError) {
        super(requestMessage, response);
        this.assertionError = assertionError;
    }

    public AssertionError getAssertionError() {
        return assertionError;
    }
}
