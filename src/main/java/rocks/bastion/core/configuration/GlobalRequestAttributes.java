package rocks.bastion.core.configuration;

import rocks.bastion.core.ApiHeader;
import rocks.bastion.core.ApiQueryParam;
import rocks.bastion.core.HttpRequest;
import rocks.bastion.core.RouteParam;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

/**
 * Request attributes that can be applied to all HTTP requests created by Bastion.
 */
public class GlobalRequestAttributes {

    private Collection<ApiHeader> globalHeaders;
    private Collection<ApiQueryParam> globalQueryParams;
    private Collection<RouteParam> globalRouteParams;
    private long globalRequestTimeout;

    public GlobalRequestAttributes() {
        clear();
    }

    /**
     * Resets all the global request attributes to their default values.
     */
    public void clear() {
        globalHeaders = new ArrayList<>();
        globalQueryParams = new ArrayList<>();
        globalRouteParams = new ArrayList<>();
        globalRequestTimeout = 0;
    }

    /**
     * Returns the configured global headers. The default value for this configuration is an empty list.
     *
     * @see HttpRequest#headers()
     */
    public Collection<ApiHeader> getGlobalHeaders() {
        return globalHeaders;
    }

    /**
     * Sets the request headers for all Bastion requests. The supplied collection will replace any previously
     * configured global headers.
     *
     * @see HttpRequest#headers()
     */
    public GlobalRequestAttributes setGlobalHeaders(Collection<ApiHeader> globalHeaders) {
        requireNonNull(globalHeaders, "globalHeaders should not be null.");
        this.globalHeaders = globalHeaders;
        return this;
    }

    /**
     * Returns the configured global query parameters. The default value for this configuration is an empty list.
     *
     * @see HttpRequest#queryParams()
     */
    public Collection<ApiQueryParam> getGlobalQueryParams() {
        return globalQueryParams;
    }

    /**
     * Sets the request query parameters for all Bastion requests. The supplied collection will replace any previously
     * configured global query parameters.
     *
     * @see HttpRequest#queryParams()
     */
    public GlobalRequestAttributes setGlobalQueryParams(Collection<ApiQueryParam> globalQueryParams) {
        requireNonNull(globalQueryParams, "globalQueryParams should not be null.");
        this.globalQueryParams = globalQueryParams;
        return this;
    }

    /**
     * Returns the configured global route parameters. The default value for this configuration is an empty list.
     *
     * @see HttpRequest#routeParams()
     */
    public Collection<RouteParam> getGlobalRouteParams() {
        return globalRouteParams;
    }

    /**
     * Sets the request route parameters for all Bastion requests. The supplied collection will replace any previously
     * configured global route parameters.
     *
     * @see HttpRequest#routeParams()
     */
    public GlobalRequestAttributes setGlobalRouteParams(Collection<RouteParam> globalRouteParams) {
        requireNonNull(globalRouteParams, "globalRouteParams should not be null.");
        this.globalRouteParams = globalRouteParams;
        return this;
    }

    /**
     * Returns the configured global timeout. The default value for this is 0, indicating no timeout.
     *
     * @see HttpRequest#timeout()
     */
    public long getGlobalRequestTimeout() {
        return globalRequestTimeout;
    }

    /**
     * Sets the timeout (in milliseconds) for all Bastion requests. A value of 0 indicates no timeout.
     *
     * @see HttpRequest#timeout()
     */
    public GlobalRequestAttributes setGlobalRequestTimeout(long globalRequestTimeout) {
        requireNonNull(globalRequestTimeout, "globalRequestTimeout should not be null.");
        this.globalRequestTimeout = globalRequestTimeout;
        return this;
    }

    /**
     * Adds a header for all Bastion requests.
     *
     * @see HttpRequest#headers()
     */
    public GlobalRequestAttributes addHeader(String name, String value) {
        requireNonNull(name, "Header name should not be null.");
        requireNonNull(value, "Header value should not be null.");
        globalHeaders.add(new ApiHeader(name, value));
        return this;
    }

    /**
     * Removes the header from all Bastion requests if it was already present.
     *
     * @see HttpRequest#headers()
     */
    public GlobalRequestAttributes removeHeader(String name, String value) {
        requireNonNull(name, "Header name should not be null.");
        requireNonNull(value, "Header value should not be null.");
        globalHeaders.remove(new ApiHeader(name, value));
        return this;
    }

    /**
     * Adds a query parameter for all Bastion requests.
     *
     * @see HttpRequest#queryParams()
     */
    public GlobalRequestAttributes addQueryParam(String name, String value) {
        requireNonNull(name, "Query parameter name should not be null.");
        requireNonNull(value, "Query parameter value should not be null.");
        globalQueryParams.add(new ApiQueryParam(name, value));
        return this;
    }

    /**
     * Removes the query parameter from all Bastion requests if it was already present.
     *
     * @see HttpRequest#queryParams()
     */
    public GlobalRequestAttributes removeQueryParam(String name, String value) {
        requireNonNull(name, "Query parameter name should not be null.");
        requireNonNull(value, "Query parameter value should not be null.");
        globalQueryParams.remove(new ApiQueryParam(name, value));
        return this;
    }

    /**
     * Adds a route parameter for all Bastion requests.
     *
     * @see HttpRequest#routeParams() ()
     */
    public GlobalRequestAttributes addRouteParam(String name, String value) {
        requireNonNull(name, "Route parameter name should not be null.");
        requireNonNull(value, "Route parameter value should not be null.");
        globalRouteParams.add(new RouteParam(name, value));
        return this;
    }

    /**
     * Removes the route parameter from all Bastion requests if it was already present.
     *
     * @see HttpRequest#routeParams()
     */
    public GlobalRequestAttributes removeRouteParam(String name, String value) {
        requireNonNull(name, "Route parameter name should not be null.");
        requireNonNull(value, "Route parameter value should not be null.");
        globalRouteParams.remove(new RouteParam(name, value));
        return this;
    }

    /**
     * Sets the timeout (in milliseconds) for all Bastion requests. A value of 0 indicates no timeout.
     *
     * @see HttpRequest#timeout()
     */
    public GlobalRequestAttributes timeout(long timeout) {
        requireNonNull(timeout, "timeout should not be null.");
        globalRequestTimeout = timeout;
        return this;
    }
}
