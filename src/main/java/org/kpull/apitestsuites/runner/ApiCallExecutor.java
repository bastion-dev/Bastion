package org.kpull.apitestsuites.runner;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.kpull.apitestsuites.core.ApiCall;
import org.kpull.apitestsuites.core.ApiEnvironment;
import org.kpull.apitestsuites.core.ApiRequest;

import java.util.Objects;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class ApiCallExecutor {

    private ApiEnvironment environment;
    private ApiCall apiCallToExecute;

    public ApiCallExecutor(ApiEnvironment environment, ApiCall apiCallToExecute) {
        Objects.requireNonNull(environment);
        Objects.requireNonNull(apiCallToExecute);
        this.environment = environment;
        this.apiCallToExecute = apiCallToExecute;
    }

    public ApiEnvironment getEnvironment() {
        return environment;
    }

    public ApiCall getApiCallToExecute() {
        return apiCallToExecute;
    }

    public void execute() {
        ApiRequest request = apiCallToExecute.getRequest();
        HttpRequest httpRequest = createHttpRequest(request);
        if (httpRequest instanceof HttpRequestWithBody) {
            ((HttpRequestWithBody) httpRequest).body(environment.process(request.getBody()));
        }
        request.getHeaders().forEach(header -> {
            httpRequest.header(header.getName(), environment.process(header.getValue()));
        });
    }

    private HttpRequest createHttpRequest(ApiRequest request) {
        HttpRequest httpRequest = null;
        switch (request.getMethod()) {
            case "POST":
                httpRequest = Unirest.post(environment.process(request.getUrl()));
                break;
            case "GET":
                httpRequest = Unirest.get(environment.process(request.getUrl()));
                break;
        }
        return httpRequest;
    }
}
