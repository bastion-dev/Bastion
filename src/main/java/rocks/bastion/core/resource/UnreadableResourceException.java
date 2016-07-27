package rocks.bastion.core.resource;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class UnreadableResourceException extends InvalidResourceException {

    public UnreadableResourceException(String source) {
        super(String.format("The specified resource is unreadable: %s", source), source);
    }
}
