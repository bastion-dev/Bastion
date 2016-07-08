package rocks.bastion.support;

import com.google.gson.Gson;
import org.apache.http.entity.ContentType;
import rocks.bastion.core.ApiHeader;
import rocks.bastion.core.ApiQueryParam;
import rocks.bastion.core.HttpMethod;
import rocks.bastion.core.Request;
import rocks.bastion.support.embedded.Sushi;

import java.util.Collection;
import java.util.Collections;

public class CreateSushiRequest implements Request {

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
    public ContentType contentType() {
        return ContentType.APPLICATION_JSON;
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
    public Object body() {
        return new Gson().toJson(Sushi.newSushi().name("happiness").build());
    }

}
