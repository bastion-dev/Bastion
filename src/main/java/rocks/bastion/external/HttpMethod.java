package rocks.bastion.external;

import java.util.Objects;

/**
 */
public class HttpMethod {

    public static final HttpMethod POST = new HttpMethod("POST");
    public static final HttpMethod GET = new HttpMethod("GET");
    public static final HttpMethod PUT = new HttpMethod("PUT");
    public static final HttpMethod DELETE = new HttpMethod("DELETE");
    public static final HttpMethod OPTIONS = new HttpMethod("OPTIONS");
    public static final HttpMethod HEAD = new HttpMethod("HEAD");

    private final String value;

    public HttpMethod(String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HttpMethod that = (HttpMethod) o;

        return getValue().equals(that.getValue());

    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }
}
