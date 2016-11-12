package rocks.bastion.core.configuration;

/**
 * TODO document class
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
