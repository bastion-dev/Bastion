package org.kpull.bastion.support.embedded;

import java.math.BigDecimal;

/**
 * A model object that represents a single Sushi object for the SushiService testing API.
 */
public class Sushi {

    private long id;
    private String name;
    private BigDecimal price;
    private Type type;

    protected Sushi() {
        // required for deserialization
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public Type getType() {
        return type;
    }

    public void setType(final Type type) {
        this.type = type;
    }

    private Sushi(final Builder builder) {
        id = builder.id;
        name = builder.name;
        price = builder.price;
        type = builder.type;
    }

    public static Builder newSushi() {
        return new Builder();
    }

    public enum Type {
        NIGIRI,
        SASHIMI,
        MAKI
    }

    public static final class Builder {

        private long id;
        private String name;
        private BigDecimal price;
        private Type type;

        public Builder() {
        }

        public Builder id(final long val) {
            id = val;
            return this;
        }


        public Builder name(final String val) {
            name = val;
            return this;
        }

        public Builder price(final BigDecimal val) {
            price = val;
            return this;
        }

        public Builder price(final long val) {
            price = new BigDecimal(val);
            return this;
        }

        public Builder type(final Type val) {
            type = val;
            return this;
        }

        public Sushi build() {
            return new Sushi(this);
        }
    }
}