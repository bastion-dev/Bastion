package rocks.bastion.core.event;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class BastionFailureEvent extends BastionEvent {

    private AssertionError assertionError;

    public BastionFailureEvent(String requestMessage, AssertionError assertionError) {
        super(requestMessage);
        this.assertionError = assertionError;
    }

    public AssertionError getAssertionError() {
        return assertionError;
    }
}
