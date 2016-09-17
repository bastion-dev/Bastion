package rocks.bastion.core;

/**
 * <p>
 * Represents a key-value pair describing an assignment for a URL route parameter. Route params are given as variables in
 * the request's URL. For example, the following URL:
 * </p>
 * <p>
 * {@code http://sushi.test/{id}/ingredients}
 * <p>
 * The URL above contains one route parameter called "id" which can be assigned a numerical value which will be replaced
 * when the actual HTTP takes place.
 * </p>
 */
public class RouteParam extends ApiProperty {

    public RouteParam(String name, String value) {
        super(name, value);
    }

    private RouteParam(){
        // required for serialization
    }
}
