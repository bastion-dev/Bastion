package rocks.bastion.core.configuration;

import java.util.Collection;

import rocks.bastion.core.ApiHeader;
import rocks.bastion.core.ApiQueryParam;
import rocks.bastion.core.RouteParam;

/**
 * TODO document class
 */
public interface Configuration {

    Collection<ApiHeader> globalHeaders();

    Collection<ApiQueryParam> globalQueryParams();

    Collection<RouteParam> globalRouteParams();

}