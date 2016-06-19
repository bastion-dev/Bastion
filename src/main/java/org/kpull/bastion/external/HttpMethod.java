package org.kpull.bastion.external;

/**
 */
public class HttpMethod {

    public static final HttpMethod POST = new HttpMethod("POST");
    public static final HttpMethod GET = new HttpMethod("GET");

    private final String value;

    public HttpMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
