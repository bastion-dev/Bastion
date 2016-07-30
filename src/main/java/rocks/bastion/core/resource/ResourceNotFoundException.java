package rocks.bastion.core.resource;

/**
 * Thrown by the {@link ResourceLoader} when the specified resource source specified by the user cannot
 * be found.
 */
public class ResourceNotFoundException extends InvalidResourceException {

    /**
     * Constructs an instance of this exception given the the source path which the {@link ResourceLoader} tried to load.
     *
     * @param source The path supplied by the user which could not be loaded.
     */
    public ResourceNotFoundException(String source) {
        super(String.format("The specified resource does not exist: %s", source), source);
    }

}
