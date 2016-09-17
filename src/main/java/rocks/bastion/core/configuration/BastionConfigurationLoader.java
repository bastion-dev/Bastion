package rocks.bastion.core.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import rocks.bastion.core.resource.ResourceLoader;

import java.io.IOException;

/**
 * Loads a bastion configuration from the specified resource.
 */
public class BastionConfigurationLoader {

    private static ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    public static Configuration load(String resourceLocation) {
        String resourceContent = new ResourceLoader(resourceLocation).load();
        try {
            return mapper.readValue(resourceContent, Configuration.class);
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Could not parse configuration content [%s] from resource [%s].", resourceContent, resourceLocation), e);
        }
    }
}
