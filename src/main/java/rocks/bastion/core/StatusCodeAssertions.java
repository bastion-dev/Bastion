package rocks.bastion.core;

import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.assertj.core.util.IterableUtil;
import rocks.bastion.core.Assertions;
import rocks.bastion.core.ModelResponse;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Performs assertions on a response by checking for the HTTP status code. The user supplies either one status code or
 * multiple status codes for which the response will pass the assertions.
 */
public class StatusCodeAssertions implements Assertions<Object> {

    public static StatusCodeAssertions of(int... statusCodes) {
        return new StatusCodeAssertions(statusCodes);
    }

    public static StatusCodeAssertions of(Integer... statusCodes) {
        return of(ArrayUtils.toPrimitive(statusCodes));
    }

    public static StatusCodeAssertions of(Iterable<Integer> statusCodes) {
        return of(Iterables.toArray(statusCodes, Integer.class));
    }

    private final int[] statusCodes;

    private StatusCodeAssertions(int... statusCodes) {
        Objects.requireNonNull(statusCodes);
        if (statusCodes.length == 0) {
            throw new IllegalArgumentException("The given list of status codes cannot be empty");
        }
        this.statusCodes = statusCodes;
    }

    @Override
    public void execute(int statusCode, ModelResponse<?> response, Object model) throws AssertionError {
        assertThat(statusCode).describedAs("HTTP Response Status Code").isIn(Arrays.asList(statusCodes));
    }
}
