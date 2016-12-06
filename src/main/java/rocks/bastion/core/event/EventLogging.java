package rocks.bastion.core.event;

import rocks.bastion.core.printer.HttpRequestPrinter;
import rocks.bastion.core.printer.ResponsePrinter;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class EventLogging {

    private static final Logger LOG = Logger.getLogger("Bastion");

    private BastionEvent event;

    public EventLogging(BastionEvent event) {
        Objects.requireNonNull(event);
        this.event = event;
    }

    public void logResponseAndRequest() {
        logResponse();
        logRequest();
    }

    public void logRequest() {
        if (event.getRequest() == null) {
            return;
        }
        LOG.info("\nRequest sent:\n==================\n" + new HttpRequestPrinter(event.getRequest()).getAsString() + "\n\n");
    }

    public void logResponse() {
        if (event.getResponse() == null) {
            return;
        }
        LOG.info("\nResponse received:\n==================\n" + new ResponsePrinter(event.getResponse()).getAsString() + "\n\n");
    }

}
