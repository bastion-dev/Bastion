package rocks.bastion;

import rocks.bastion.core.BastionFactory;
import rocks.bastion.core.HttpRequest;
import rocks.bastion.core.builder.BastionBuilder;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public final class Bastion {

    public static BastionBuilder<String> request(String message, HttpRequest request) {
        return BastionFactory.getDefaultBastionFactory().getBastion(message, request);
    }

    private Bastion() {
    }

}
