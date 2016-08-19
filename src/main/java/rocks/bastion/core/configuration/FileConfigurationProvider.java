package rocks.bastion.core.configuration;

import java.util.Objects;

import rocks.bastion.core.resource.ResourceLoader;

/**
 * TODO document class
 */
public class FileConfigurationProvider implements ConfigurationProvider {

    private static final String DEFAULT_CONFIG_FILE_NAME = "bastion.yml";

    private ConfigurationImpl configuration;

    public static FileConfigurationProvider load(String fileName) {
        Objects.requireNonNull(fileName);
        ConfigurationImpl configuration = parseConfiguration(new ResourceLoader(fileName).load());
        return new FileConfigurationProvider(configuration);
    }

    public static FileConfigurationProvider loadDefault() {
        ConfigurationImpl configuration = parseConfiguration(new ResourceLoader(DEFAULT_CONFIG_FILE_NAME).load());
        return new FileConfigurationProvider(configuration);
    }

    private FileConfigurationProvider(ConfigurationImpl configuration) {
        this.configuration = configuration;
    }

    private FileConfigurationProvider() {
        configuration = new ConfigurationImpl();
    }

    private static ConfigurationImpl parseConfiguration(final String configurationString) {
        // TODO  parse it
        return new ConfigurationImpl();
    }

    @Override
    public Configuration get() {
        return configuration;
    }
}
