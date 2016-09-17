package rocks.bastion.core.configuration;

/**
 * TODO document class
 */
public class Configuration {

    public Configuration() {}

    private GlobalRequestAttributes globalRequestAttributes;

    public GlobalRequestAttributes getGlobalRequestAttributes() {
        return globalRequestAttributes;
    }

    public void setGlobalRequestAttributes(GlobalRequestAttributes globalRequestAttributes) {
        this.globalRequestAttributes = globalRequestAttributes;
    }
}