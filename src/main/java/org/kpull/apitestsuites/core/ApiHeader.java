package org.kpull.apitestsuites.core;

import java.util.Objects;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class ApiHeader {

    private String name;
    private String value;

    public ApiHeader(String name, String value) {
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

        ApiHeader apiHeader = (ApiHeader) o;

        if (getName() != null ? !getName().equals(apiHeader.getName()) : apiHeader.getName() != null) return false;
        return getValue() != null ? getValue().equals(apiHeader.getValue()) : apiHeader.getValue() == null;

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}
