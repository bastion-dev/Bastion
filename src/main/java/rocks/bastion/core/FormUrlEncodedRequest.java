package rocks.bastion.core;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import rocks.bastion.core.json.InvalidJsonException;

import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */
public class FormUrlEncodedRequest implements HttpRequest {

    public static FormUrlEncodedRequest withMethod(HttpMethod method, String url) {
        return new FormUrlEncodedRequest(method, url);
    }

    public static FormUrlEncodedRequest post(String url) {
        return new FormUrlEncodedRequest(HttpMethod.POST, url);
    }

    public static FormUrlEncodedRequest put(String url) {
        return new FormUrlEncodedRequest(HttpMethod.PUT, url);
    }

    public static FormUrlEncodedRequest delete(String url) {
        return new FormUrlEncodedRequest(HttpMethod.DELETE, url);
    }

    public static FormUrlEncodedRequest patch(String url) {
        return new FormUrlEncodedRequest(HttpMethod.PATCH, url);
    }

    private CommonRequestAttributes requestAttributes;
    private List<ApiDataParameter> dataParameters;

    protected FormUrlEncodedRequest(HttpMethod method, String url) throws InvalidJsonException {
        Objects.requireNonNull(method);
        Objects.requireNonNull(url);

        requestAttributes = new CommonRequestAttributes(method, url, "");
        requestAttributes.setContentType(ContentType.APPLICATION_FORM_URLENCODED);

        dataParameters = new LinkedList<>();
    }

    public void addDataParameter(String name, String value) {
        dataParameters.add(new ApiDataParameter(name, value));
        recomputeBody();
    }

    public void addDataParameters(Map<String, String> parameters) {
        List<ApiDataParameter> listFromMap = parameters.entrySet().stream().map(entry ->
                new ApiDataParameter(entry.getKey(), entry.getValue())).collect(Collectors.toList());
        addDataParameters(listFromMap);
    }

    public void addDataParameters(Iterable<ApiDataParameter> parameters) {
        for (ApiDataParameter parameter : parameters) {
            dataParameters.add(new ApiDataParameter(parameter.getName(), parameter.getValue()));
        }
        recomputeBody();
    }

    /**
     * Override the content-type that will be used for this request. Initially, the content-type for a {@code FormUrlEncodedRequest}
     * is "application/x-www-form-urlencoded" but you can override what is sent using this method.
     *
     * @param contentType A content-type to use for this request. Can be {@literal null}.
     * @return This request (for method chaining)
     */
    public FormUrlEncodedRequest overrideContentType(ContentType contentType) {
        requestAttributes.setContentType(contentType);
        // Recompute the body because the charset could have changed
        recomputeBody();
        return this;
    }

    /**
     * Add a new HTTP header that will be sent with this request.
     *
     * @param name  A non-{@literal null} name for the new header
     * @param value A non-{@literal null} value for the new header
     * @return This request (for method chaining)
     */
    public FormUrlEncodedRequest addHeader(String name, String value) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(value);
        requestAttributes.addHeader(name, value);
        return this;
    }

    /**
     * Add a new HTTP query parameter that will be sent with this request.
     *
     * @param name  A non-{@literal null} name for the new query parameter
     * @param value A non-{@literal null} value for the new query parameter
     * @return This request (for method chaining)
     */
    public FormUrlEncodedRequest addQueryParam(String name, String value) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(value);
        requestAttributes.addQueryParam(name, value);
        return this;
    }

    /**
     * Add a new HTTP route parameter that will be sent with this request. Put a placeholder for the route parameter in
     * the request URL by surrounding a parameter's name using braces (eg. {@code http://sushi.test/{id}/ingredients}).
     * The URL in the previous example contains one route param which can be replaced with a numerical value using
     * {@code addRouteParam("id", "53")}, for example.
     *
     * @param name  A non-{@literal null} name for the new route parameter
     * @param value A non-{@literal null} value for the new route parameter
     * @return This request (for method chaining)
     */
    public FormUrlEncodedRequest addRouteParam(String name, String value) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(value);
        requestAttributes.addRouteParam(name, value);
        return this;
    }

    @Override
    public String name() {
        return requestAttributes.name();
    }

    @Override
    public String url() {
        return requestAttributes.url();
    }

    @Override
    public HttpMethod method() {
        return requestAttributes.method();
    }

    @Override
    public Optional<ContentType> contentType() {
        return requestAttributes.contentType();
    }

    @Override
    public Collection<ApiHeader> headers() {
        return requestAttributes.headers();
    }

    @Override
    public Collection<ApiQueryParam> queryParams() {
        return requestAttributes.queryParams();
    }

    @Override
    public Collection<RouteParam> routeParams() {
        return requestAttributes.routeParams();
    }

    @Override
    public Object body() {
        return requestAttributes.body();
    }

    private void recomputeBody() {
        Charset encodingCharset = getEncodingCharset();
        String urlEncodedBody = URLEncodedUtils.format(BastionUtils.propertiesToNameValuePairs(dataParameters), encodingCharset);
        requestAttributes.setBody(urlEncodedBody);
    }

    private Charset getEncodingCharset() {
        return contentType().map(ContentType::getCharset).orElse(Charset.defaultCharset());
    }
}
