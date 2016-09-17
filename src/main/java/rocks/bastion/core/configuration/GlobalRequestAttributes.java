package rocks.bastion.core.configuration;

import rocks.bastion.core.ApiHeader;
import rocks.bastion.core.ApiQueryParam;
import rocks.bastion.core.RouteParam;

import java.util.ArrayList;
import java.util.Collection;

/**
 * TODO document class
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

    public void setGlobalHeaders(Collection<ApiHeader> globalHeaders) {
        this.globalHeaders = globalHeaders;
    }

    public Collection<ApiQueryParam> getGlobalQueryParams() {
        return globalQueryParams;
    }

    public void setGlobalQueryParams(Collection<ApiQueryParam> globalQueryParams) {
        this.globalQueryParams = globalQueryParams;
    }

    public Collection<RouteParam> getGlobalRouteParams() {
        return globalRouteParams;
    }

    public void setGlobalRouteParams(Collection<RouteParam> globalRouteParams) {
        this.globalRouteParams = globalRouteParams;
    }

    public long getGlobalRequestTimeout() {
        return globalRequestTimeout;
    }

    public void setGlobalRequestTimeout(long globalRequestTimeout) {
        this.globalRequestTimeout = globalRequestTimeout;
    }
}
