package rocks.bastion.core.configuration;

import java.util.ArrayList;
import java.util.Collection;

import rocks.bastion.core.ApiHeader;
import rocks.bastion.core.ApiQueryParam;
import rocks.bastion.core.RouteParam;

/**
 * TODO document class
 */
public class ConfigurationImpl implements Configuration {

    private Collection<ApiHeader> globalHeaders;
    private Collection<ApiQueryParam> globalQueryParams;
    private Collection<RouteParam> globalRouteParams;

    public ConfigurationImpl() {
        this.globalHeaders = new ArrayList<>();
        this.globalQueryParams = new ArrayList<>();
        this.globalRouteParams = new ArrayList<>();
    }

    public ConfigurationImpl(Collection<ApiHeader> globalHeaders, Collection<ApiQueryParam> globalQueryParams, Collection<RouteParam> globalRouteParams) {
        this.globalHeaders = globalHeaders;
        this.globalQueryParams = globalQueryParams;
        this.globalRouteParams = globalRouteParams;
    }

    @Override
    public Collection<ApiHeader> globalHeaders() {
        return globalHeaders;
    }

    public void setGlobalHeaders(Collection<ApiHeader> globalHeaders) {
        this.globalHeaders = globalHeaders;
    }

    @Override
    public Collection<ApiQueryParam> globalQueryParams() {
        return globalQueryParams;
    }

    public void setGlobalQueryParams(Collection<ApiQueryParam> globalQueryParams) {
        this.globalQueryParams = globalQueryParams;
    }

    @Override
    public Collection<RouteParam> globalRouteParams() {
        return globalRouteParams;
    }

    public void setGlobalRouteParams(Collection<RouteParam> globalRouteParams) {
        this.globalRouteParams = globalRouteParams;
    }
}
