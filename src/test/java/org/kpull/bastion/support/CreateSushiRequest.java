package org.kpull.bastion.support;

import org.apache.http.entity.ContentType;
import org.kpull.bastion.core.ApiHeader;
import org.kpull.bastion.core.ApiQueryParam;
import org.kpull.bastion.external.HttpMethod;
import org.kpull.bastion.external.Request;
import org.kpull.bastion.support.embedded.Sushi;

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
        return Sushi.newSushi().name("happiness").build();
    }

}
