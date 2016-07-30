package rocks.bastion.core.resource;

/**
 * Thrown by the {@link ResourceLoader} when it attempts to load a resource which exists but cannot be read. This can be,
 * for example, a file path which points to a directory or a file which the user does not have permissions to read.
 */
public class UnreadableResourceException extends InvalidResourceException {

    /**
     * Constructs an instance of this exception given the the source path which the {@link ResourceLoader} tried to load.
     *
     * @param source The path supplied by the user which could not be loaded.
     */
    public UnreadableResourceException(String source) {
        super(String.format("The specified resource is unreadable: %s", source), source);
    }
}
