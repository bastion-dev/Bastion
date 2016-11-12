package rocks.bastion.core.configuration;

/**
 * Unchecked exception thrown when the Bastion configuration could not be loaded or has an invalid
 * structure.
 */
public class InvalidConfigurationException extends RuntimeException {

    public InvalidConfigurationException() {
        super();
    }

    public InvalidConfigurationException(String message) {
        super(message);
    }

    public InvalidConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
