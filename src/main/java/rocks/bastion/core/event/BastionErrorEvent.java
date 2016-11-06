package rocks.bastion.core.event;

import rocks.bastion.core.HttpRequest;
import rocks.bastion.core.Response;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class BastionErrorEvent extends BastionEvent {

    private Throwable throwable;

    public BastionErrorEvent(HttpRequest request, Response response, Throwable throwable) {
        super(request, response);
        this.throwable = throwable;
    }

    public Throwable getThrowable() {
        return throwable;
    }

}
