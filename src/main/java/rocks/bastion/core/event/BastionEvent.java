package rocks.bastion.core.event;

import rocks.bastion.core.HttpRequest;
import rocks.bastion.core.Response;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class BastionEvent {

    private HttpRequest request;
    private Response response;

    public BastionEvent(HttpRequest request, Response response) {
        this.request = request;
        this.response = response;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public Response getResponse() {
        return response;
    }
}
