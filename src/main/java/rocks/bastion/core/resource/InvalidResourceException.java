package rocks.bastion.core.resource;

/**
 * Thrown by the {@link ResourceLoader resource loader} by the specified resource is invalid. Typically, subclasses of
 * this exception will be thrown.
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class InvalidResourceException extends RuntimeException {

    private String source;

    /**
     * Constructs an instance of this exception given the descriptive error message to display and the source path
     * which the {@link ResourceLoader} tried to load.
     *
     * @param message The error message describing the issue.
     * @param source  The path supplied by the user which could not be loaded.
     */
    public InvalidResourceException(String message, String source) {
        super(message);
        this.source = source;
    }

    /**
     * Gets the source patch which the {@link ResourceLoader} tried to load when throwing this exception.
     *
     * @return The user-supplied source path to load.
     */
    public String getSource() {
        return source;
    }
}
