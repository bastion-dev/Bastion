package rocks.bastion.core.event;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class BastionErrorEvent extends BastionEvent {

    private Throwable throwable;

    public BastionErrorEvent(String requestMessage, Throwable throwable) {
        super(requestMessage);
        this.throwable = throwable;
    }

    public Throwable getThrowable() {
        return throwable;
    }

}
