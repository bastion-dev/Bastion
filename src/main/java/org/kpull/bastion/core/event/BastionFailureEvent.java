package org.kpull.bastion.core.event;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class BastionFailureEvent {

    private AssertionError assertionError;

    public BastionFailureEvent(AssertionError assertionError) {
        this.assertionError = assertionError;
    }

    public AssertionError getAssertionError() {
        return assertionError;
    }
}
