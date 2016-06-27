package rocks.bastion.core;

import org.apache.http.entity.ContentType;

import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;

/**
 * Represents an HTTP response which also has a response model object. The response model is bound from the content/body
 * of the HTTP response.
 *
 * @param <MODEL> The model object type which was bound for this HTTP response.
 */
public class ModelResponse<MODEL> implements Response {

    private Response response;
    private MODEL model;

    public ModelResponse(Response response, MODEL model) {
        this.response = response;
        this.model = model;
    }

    @Override
    public Optional<ContentType> getContentType() {
        return response.getContentType();
    }

    @Override
    public int getStatusCode() {
        return response.getStatusCode();
    }

    @Override
    public String getStatusText() {
        return response.getStatusText();
    }

    @Override
    public Collection<ApiHeader> getHeaders() {
        return response.getHeaders();
    }

    @Override
    public InputStream getBody() {
        return response.getBody();
    }

    public MODEL getModel() {
        return model;
    }
}
