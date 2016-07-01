package rocks.bastion.core.request;

import static java.lang.String.format;

/**
 * Indicates that the JSON provided for a {@link JsonRequest} is not valid JSON.
 */
public class InvalidJsonException extends RuntimeException {

    private String invalidJson;

    public InvalidJsonException(String invalidJson) {
        super(format("The following text is not valid JSON text: %s", invalidJson));
        this.invalidJson = invalidJson;
    }

    public InvalidJsonException(String message, String invalidJson) {
        super(message);
        this.invalidJson = invalidJson;
    }

    public InvalidJsonException(String message, Throwable cause, String invalidJson) {
        super(message, cause);
        this.invalidJson = invalidJson;
    }

    public InvalidJsonException(Throwable cause, String invalidJson) {
        super(format("The following text is not valid JSON text: %s", invalidJson), cause);
        this.invalidJson = invalidJson;
    }

    /**
     * Gets the text found which could not be parsed as valid JSON.
     *
     * @return Invalid JSON text
     */
    public String getInvalidJson() {
        return invalidJson;
    }
}
