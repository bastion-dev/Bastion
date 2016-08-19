package rocks.bastion.core.configuration;

import java.util.Collection;
import java.util.Objects;

import rocks.bastion.core.ApiHeader;
import rocks.bastion.core.ApiQueryParam;
import rocks.bastion.core.RouteParam;

/**
 * TODO document class, fill in requireNonNull() messages
 */
public class ConfigurationBuilder implements ConfigurationProvider {

    private ConfigurationImpl configuration;

    private ConfigurationBuilder() {
        configuration = new ConfigurationImpl();
    }

    public static ConfigurationBuilder startConfiguration() {
        return new ConfigurationBuilder();
    }

    public ConfigurationBuilder addHeader(String name, String value) {
        Objects.requireNonNull(name, "Header name cannot be null.");
        Objects.requireNonNull(value, "Header value cannot be null.");
        this.configuration.globalHeaders().add(new ApiHeader(name, value));
        return this;
    }

    public ConfigurationBuilder removeHeader(String name) {
        Objects.requireNonNull(name, "Header name cannot be null.");
        this.configuration.globalHeaders().removeIf(header -> header.getName().equals(name));
        return this;
    }

    public ConfigurationBuilder setHeaders(Collection<ApiHeader> apiHeaders) {
        Objects.requireNonNull(apiHeaders, "Headers cannot be null.");
        this.configuration.setGlobalHeaders(apiHeaders);
        return this;
    }

    public ConfigurationBuilder addQueryParam(String name, String value) {
        Objects.requireNonNull(name, "Query parameter name cannot be null.");
        Objects.requireNonNull(value, "Query parameter value cannot be null.");
        this.configuration.globalQueryParams().add(new ApiQueryParam(name, value));
        return this;
    }

    public ConfigurationBuilder removeQueryParam(String name) {
        Objects.requireNonNull(name, "Query parameter name cannot be null.");
        this.configuration.globalQueryParams().removeIf(queryParam -> queryParam.getName().equals(name));
        return this;
    }

    public ConfigurationBuilder setQueryParams(Collection<ApiQueryParam> queryParams) {
        Objects.requireNonNull(queryParams, "Query parameters cannot be null.");
        this.configuration.setGlobalQueryParams(queryParams);
        return this;
    }

    public ConfigurationBuilder addRouteParam(String name, String value) {
        Objects.requireNonNull(name, "Route parameter name cannot be null.");
        Objects.requireNonNull(value, "Route parameter value cannot be null.");
        this.configuration.globalRouteParams().add(new RouteParam(name, value));
        return this;
    }

    public ConfigurationBuilder removeRouteParam(String name) {
        Objects.requireNonNull(name, "Route parameter name cannot be null.");
        this.configuration.globalRouteParams().removeIf(routeParam -> routeParam.getName().equals(name));
        return this;
    }

    public ConfigurationBuilder setRouteParams(Collection<RouteParam> routeParams) {
        Objects.requireNonNull(routeParams, "Route parameters cannot be null.");
        this.configuration.setGlobalRouteParams(routeParams);
        return this;
    }

    @Override
    public Configuration get() {
        return configuration;
    }
}
