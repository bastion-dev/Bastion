package rocks.bastion.core.resource;

/**
 * Thrown by the {@link ResourceLoader} when the specified resource source specified by the user cannot
 * be found.
 *
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class ResourceNotFoundException extends InvalidResourceException {

    public ResourceNotFoundException(String source) {
        super(String.format("The specified resource does not exist: %s", source), source);
    }

}
