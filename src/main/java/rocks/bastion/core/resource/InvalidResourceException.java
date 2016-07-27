package rocks.bastion.core.resource;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class InvalidResourceException extends RuntimeException {

    protected String source;

    public InvalidResourceException(String message, String source) {
        super(message);
        this.source = source;
    }

    public String getSource() {
        return source;
    }
}
