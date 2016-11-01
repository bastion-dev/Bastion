package rocks.bastion.core;

import com.google.common.collect.Iterables;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Performs assertions on a response by checking for the HTTP status code. The user supplies either one status code or
 * multiple status codes for which the response will pass the assertions.
 */
public final class StatusCodeAssertions implements Assertions<Object> {

    /**
     * Initialise a assertions object expecting the HTTP response status code to be equal to any one of the given status codes.
     *
     * @param expectedStatusCodes The non-empty array of status codes to match against
     * @return An assertions object for use with the {@link rocks.bastion.core.builder.AssertionsBuilder#withAssertions(Assertions)} method
     * @throws IllegalArgumentException Thrown if the supplied array is empty
     */
    public static StatusCodeAssertions expecting(int... expectedStatusCodes) {
        return new StatusCodeAssertions(expectedStatusCodes);
    }

    /**
     * Initialise a assertions object expecting the HTTP response status code to be equal to any one of the given status codes.
     *
     * @param expectedStatusCodes The non-empty array of status codes to match against
     * @return An assertions object for use with the {@link rocks.bastion.core.builder.AssertionsBuilder#withAssertions(Assertions)} method
     * @throws IllegalArgumentException Thrown if the supplied array is empty
     */
    public static StatusCodeAssertions expecting(Integer... expectedStatusCodes) {
        return expecting(ArrayUtils.toPrimitive(expectedStatusCodes));
    }

    /**
     * Initialise a assertions object expecting the HTTP response status code to be equal to any one of the given status codes.
     *
     * @param expectedStatusCodes The non-empty iterable of status codes to match against
     * @return An assertions object for use with the {@link rocks.bastion.core.builder.AssertionsBuilder#withAssertions(Assertions)} method
     * @throws IllegalArgumentException Thrown if the supplied array is empty
     */
    public static StatusCodeAssertions expecting(Iterable<Integer> expectedStatusCodes) {
        return expecting(Iterables.toArray(expectedStatusCodes, Integer.class));
    }

    private final int[] expectedStatusCodes;

    private StatusCodeAssertions(int... expectedStatusCodes) {
        Objects.requireNonNull(expectedStatusCodes);
        if (expectedStatusCodes.length == 0) {
            throw new IllegalArgumentException("The given list of status codes cannot be empty");
        }
        this.expectedStatusCodes = expectedStatusCodes;
    }

    @Override
    public void execute(int statusCode, ModelResponse<?> response, Object model) throws AssertionError {
        assertThat(statusCode).describedAs("HTTP Response Status Code").matches(actualStatusCode ->
                ArrayUtils.contains(expectedStatusCodes, actualStatusCode));
    }
}
