package rocks.bastion.core.configuration;

import rocks.bastion.core.ApiHeader;
import rocks.bastion.core.ApiQueryParam;
import rocks.bastion.core.RouteParam;
import rocks.bastion.core.json.JsonRequest;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Request attributes that can be applied to all HTTP requests created by Bastion.
 */
public class GlobalRequestAttributes {

    private Collection<ApiHeader> globalHeaders;
    private Collection<ApiQueryParam> globalQueryParams;
    private Collection<RouteParam> globalRouteParams;
    private long globalRequestTimeout;

    public GlobalRequestAttributes() {
        globalHeaders = new ArrayList<>();
        globalQueryParams = new ArrayList<>();
        globalRouteParams = new ArrayList<>();
    }

    public GlobalRequestAttributes(Collection<ApiHeader> globalHeaders, Collection<ApiQueryParam> globalQueryParams, Collection<RouteParam> globalRouteParams, long globalRequestTimeout) {
        this.globalHeaders = globalHeaders;
        this.globalQueryParams = globalQueryParams;
        this.globalRouteParams = globalRouteParams;
        this.globalRequestTimeout = globalRequestTimeout;
    }

    public Collection<ApiHeader> getGlobalHeaders() {
        return globalHeaders;
    }

    public GlobalRequestAttributes setGlobalHeaders(Collection<ApiHeader> globalHeaders) {
        this.globalHeaders = globalHeaders;
        return this;
    }

    public Collection<ApiQueryParam> getGlobalQueryParams() {
        return globalQueryParams;
    }

    public GlobalRequestAttributes setGlobalQueryParams(Collection<ApiQueryParam> globalQueryParams) {
        this.globalQueryParams = globalQueryParams;
        return this;
    }

    public Collection<RouteParam> getGlobalRouteParams() {
        return globalRouteParams;
    }

    public GlobalRequestAttributes setGlobalRouteParams(Collection<RouteParam> globalRouteParams) {
        this.globalRouteParams = globalRouteParams;
        return this;
    }

    public long getGlobalRequestTimeout() {
        return globalRequestTimeout;
    }

    public GlobalRequestAttributes setGlobalRequestTimeout(long globalRequestTimeout) {
        this.globalRequestTimeout = globalRequestTimeout;
        return this;
    }

    public GlobalRequestAttributes addHeader(String name, String value) {
        globalHeaders.add(new ApiHeader(name, value));
        return this;

    }

    public GlobalRequestAttributes removeHeader(String name, String value) {
        globalHeaders.remove(new ApiHeader(name, value));
        return this;
    }

    public GlobalRequestAttributes addQueryParam(String name, String value) {
        globalQueryParams.add(new ApiQueryParam(name, value));
        return this;
    }

    public GlobalRequestAttributes removeQueryParam(String name, String value) {
        globalQueryParams.remove(new ApiQueryParam(name, value));
        return this;
    }

    public GlobalRequestAttributes addRouteParam(String name, String value) {
        globalRouteParams.add(new RouteParam(name, value));
        return this;
    }

    public GlobalRequestAttributes removeRouteParam(String name, String value) {
        globalRouteParams.remove(new RouteParam(name, value));
        return this;
    }

    public GlobalRequestAttributes timeout(long timeout) {
        globalRequestTimeout = timeout;
        return this;
    }
}
