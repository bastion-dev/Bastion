package rocks.bastion.support;

import com.google.gson.Gson;
import org.apache.http.entity.ContentType;
import rocks.bastion.core.*;
import rocks.bastion.support.embedded.Sushi;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class CreateSushiRequest implements HttpRequest {

    @Override
    public String name() {
        return "Create Sushi Request";
    }

    @Override
    public String url() {
        return "http://localhost:9876/sushi";
    }

    @Override
    public HttpMethod method() {
        return HttpMethod.POST;
    }

    @Override
    public Optional<ContentType> contentType() {
        return Optional.of(ContentType.APPLICATION_JSON);
    }

    @Override
    public Collection<ApiHeader> headers() {
        return Collections.emptyList();
    }

    @Override
    public Collection<ApiQueryParam> queryParams() {
        return Collections.emptyList();
    }

    @Override
    public Collection<RouteParam> routeParams() {
        return Collections.emptyList();
    }

    @Override
    public Object body() {
        return new Gson().toJson(Sushi.newSushi().name("happiness").build());
    }

}
