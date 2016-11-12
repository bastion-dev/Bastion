package rocks.bastion.core.configuration;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.util.Objects;

import rocks.bastion.core.resource.ResourceLoader;

/**
 * Loads a bastion configuration from the specified resource.
 */
public class BastionConfigurationLoader {

    private static YAMLMapper mapper = new YAMLMapper();

    private String resourceLocation;

    public BastionConfigurationLoader(String resourceLocation) {
        Objects.requireNonNull(resourceLocation, "Resource location should not be null");
        this.resourceLocation = resourceLocation;
    }

    public Configuration load() {
        String resourceContent = new ResourceLoader(resourceLocation).load();
        try {
            return mapper.readValue(resourceContent, Configuration.class);
        } catch (JsonParseException e) {
            throw new InvalidConfigurationException(String.format("Could not parse configuration content [%s] from resource [%s]. Make sure it is valid YAML.", resourceContent, resourceLocation), e);
        } catch (JsonMappingException e) {
            throw new InvalidConfigurationException(String.format("The configuration content [%s] from resource [%s] does not match the expected configuration structure.", resourceContent, resourceLocation), e);
        } catch (IOException e) {
            throw new IllegalStateException(String.format("An unknown error occurred while reading the content from resource [%s}", resourceLocation));
        }
    }
}
