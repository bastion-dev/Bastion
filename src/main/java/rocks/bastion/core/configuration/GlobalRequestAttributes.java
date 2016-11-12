package rocks.bastion.core.configuration;

import java.util.ArrayList;
import java.util.Collection;

import rocks.bastion.core.ApiHeader;
import rocks.bastion.core.ApiQueryParam;
import rocks.bastion.core.RouteParam;

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
        setGlobalHeaders(new ArrayList<>());
        setGlobalQueryParams(new ArrayList<>());
        setGlobalRouteParams(new ArrayList<>());
        setGlobalRequestTimeout(0);
    }

    public GlobalRequestAttributes(Collection<ApiHeader> globalHeaders, Collection<ApiQueryParam> globalQueryParams, Collection<RouteParam> globalRouteParams, long globalRequestTimeout) {
        setGlobalHeaders(globalHeaders);
        setGlobalQueryParams(globalQueryParams);
        setGlobalRouteParams(globalRouteParams);
        setGlobalRequestTimeout(globalRequestTimeout);
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

    public Collection<ApiHeader> getGlobalHeaders() {
        return globalHeaders;
    }

    public GlobalRequestAttributes setGlobalHeaders(Collection<ApiHeader> globalHeaders) {
        requireNonNull(globalHeaders, "globalHeaders should not be null.");
        this.globalHeaders = globalHeaders;
        return this;
    }

    public Collection<ApiQueryParam> getGlobalQueryParams() {
        return globalQueryParams;
    }

    public GlobalRequestAttributes setGlobalQueryParams(Collection<ApiQueryParam> globalQueryParams) {
        requireNonNull(globalQueryParams, "globalQueryParams should not be null.");
        this.globalQueryParams = globalQueryParams;
        return this;
    }

    public Collection<RouteParam> getGlobalRouteParams() {
        return globalRouteParams;
    }

    public GlobalRequestAttributes setGlobalRouteParams(Collection<RouteParam> globalRouteParams) {
        requireNonNull(globalRouteParams, "globalRouteParams should not be null.");
        this.globalRouteParams = globalRouteParams;
        return this;
    }

    public long getGlobalRequestTimeout() {
        return globalRequestTimeout;
    }

    public GlobalRequestAttributes setGlobalRequestTimeout(long globalRequestTimeout) {
        requireNonNull(globalRequestTimeout, "globalRequestTimeout should not be null.");
        this.globalRequestTimeout = globalRequestTimeout;
        return this;
    }

    public GlobalRequestAttributes addHeader(String name, String value) {
        requireNonNull(name, "Header name should not be null.");
        requireNonNull(value, "Header value should not be null.");
        globalHeaders.add(new ApiHeader(name, value));
        return this;

    }

    public GlobalRequestAttributes removeHeader(String name, String value) {
        requireNonNull(name, "Header name should not be null.");
        requireNonNull(value, "Header value should not be null.");
        globalHeaders.remove(new ApiHeader(name, value));
        return this;
    }

    public GlobalRequestAttributes addQueryParam(String name, String value) {
        requireNonNull(name, "Query parameter name should not be null.");
        requireNonNull(value, "Query parameter value should not be null.");
        globalQueryParams.add(new ApiQueryParam(name, value));
        return this;
    }

    public GlobalRequestAttributes removeQueryParam(String name, String value) {
        requireNonNull(name, "Query parameter name should not be null.");
        requireNonNull(value, "Query parameter value should not be null.");
        globalQueryParams.remove(new ApiQueryParam(name, value));
        return this;
    }

    public GlobalRequestAttributes addRouteParam(String name, String value) {
        requireNonNull(name, "Route parameter name should not be null.");
        requireNonNull(value, "Route parameter value should not be null.");
        globalRouteParams.add(new RouteParam(name, value));
        return this;
    }

    public GlobalRequestAttributes removeRouteParam(String name, String value) {
        requireNonNull(name, "Route parameter name should not be null.");
        requireNonNull(value, "Route parameter value should not be null.");
        globalRouteParams.remove(new RouteParam(name, value));
        return this;
    }

    public GlobalRequestAttributes timeout(long timeout) {
        globalRequestTimeout = timeout;
        return this;
    }
}
