package rocks.bastion.core.event;

import rocks.bastion.core.Response;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class BastionErrorEvent extends BastionEvent {

    private Throwable throwable;

    public BastionErrorEvent(String requestMessage, Response response, Throwable throwable) {
        super(requestMessage, response);
        this.throwable = throwable;
    }

    public Throwable getThrowable() {
        return throwable;
    }

}
