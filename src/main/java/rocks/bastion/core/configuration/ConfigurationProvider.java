package rocks.bastion.core.configuration;

import java.util.function.Supplier;

/**
 * TODO document class
 */
public interface ConfigurationProvider extends Supplier<Configuration> {

    @Override
    Configuration get();
}
