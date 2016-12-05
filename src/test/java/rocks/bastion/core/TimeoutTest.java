package rocks.bastion.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import rocks.bastion.Bastion;
import rocks.bastion.core.json.JsonRequest;
import rocks.bastion.support.embedded.TestWithEmbeddedServer;

import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for the timeout functionality.
 *
 * @author benjie.gatt
 */
public class TimeoutTest extends TestWithEmbeddedServer {

    @Rule
    public Stopwatch stopwatch = new Stopwatch() {
    };

    @Test(timeout = 3000L)
    public void callSlowAPI_jsonRequest_requestTimesOutAndTestFails() {
        JsonRequest request = JsonRequest.fromString(HttpMethod.GET, "http://localhost:9876/chikuzen-ni", "");
        request.setTimeout(1500L);
        performRequestAndAssert(request);
    }

    @Test(timeout = 3000L)
    public void callSlowAPI_generalRequest_requestTimesOutAndTestFails() {
        GeneralRequest request = GeneralRequest.get("http://localhost:9876/chikuzen-ni");
        request.setTimeout(1500L);
        performRequestAndAssert(request);
    }

    @Test(timeout = 3000L)
    public void callSlowAPI_formUrlEncodedRequest_requestTimesOutAndTestFails() {
        FormUrlEncodedRequest request = FormUrlEncodedRequest.withMethod(HttpMethod.GET, "http://localhost:9876/chikuzen-ni");
        request.setTimeout(1500L);
        performRequestAndAssert(request);
    }

    private void performRequestAndAssert(HttpRequest request) {
        assertThatThrownBy(() -> Bastion.request("Create Sushi", request).call())
                .isInstanceOf(AssertionError.class)
                .hasMessage(format("Failed to receive response before timeout of [%d] ms", request.timeout()));
        assertThat(stopwatch.runtime(TimeUnit.MILLISECONDS)).as("Test runtime").isGreaterThanOrEqualTo(1000L);
    }
}
