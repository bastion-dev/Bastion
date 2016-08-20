package rocks.bastion.core;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An internal class containing utility functions which do not fit anywhere else.
 */
public final class BastionUtils {

    public static List<NameValuePair> propertiesToNameValuePairs(Iterable<? extends ApiProperty> properties) {
        Objects.requireNonNull(properties);
        ArrayList<NameValuePair> mappedArray = new ArrayList<>();
        for (ApiProperty property : properties) {
            mappedArray.add(propertyToNameValuePair(property));
        }
        return mappedArray;
    }

    public static BasicNameValuePair propertyToNameValuePair(ApiProperty property) {
        Objects.requireNonNull(property);
        return new BasicNameValuePair(property.getName(), property.getValue());
    }

    private BastionUtils() {
    }

}
