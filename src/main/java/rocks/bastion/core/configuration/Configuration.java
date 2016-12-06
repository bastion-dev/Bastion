package rocks.bastion.core.configuration;

import rocks.bastion.Bastion;

/**
 * <p>Contains all Bastion configuration settings.</p>
 * <p>Bastion can be configured via a YAML file that can be loaded via the {@link BastionConfigurationLoader}, or programmatically via {@link Bastion#globals()}</p>
 */
public class Configuration {

    private GlobalRequestAttributes globalRequestAttributes;

    public Configuration() {
        globalRequestAttributes = new GlobalRequestAttributes();
    }

    public GlobalRequestAttributes getGlobalRequestAttributes() {
        return globalRequestAttributes;
    }

    public void setGlobalRequestAttributes(GlobalRequestAttributes globalRequestAttributes) {
        this.globalRequestAttributes = globalRequestAttributes;
    }
}