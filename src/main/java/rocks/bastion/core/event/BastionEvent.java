package rocks.bastion.core.event;

import rocks.bastion.core.Response;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class BastionEvent {

    private String requestMessage;
    private Response response;

    public BastionEvent(String requestMessage, Response response) {
        this.requestMessage = requestMessage;
        this.response = response;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public Response getResponse() {
        return response;
    }
}
