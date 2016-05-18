package org.kpull.bastion.core;

import java.util.Objects;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class ApiQueryParam {

    private String name;
    private String value;

    public ApiQueryParam(String name, String value) {
        setName(name);
        setValue(value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiQueryParam that = (ApiQueryParam) o;

        if (!getName().equals(that.getName())) return false;
        return getValue().equals(that.getValue());

    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getValue().hashCode();
        return result;
    }
}
