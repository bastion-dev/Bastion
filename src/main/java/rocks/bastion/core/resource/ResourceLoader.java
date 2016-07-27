package rocks.bastion.core.resource;

import com.google.common.io.ByteStreams;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * Loads resource data which is identified by a string. This string can take many forms: source strings may start with
 * a "{@code classpath:}" prefix or any other URL (such as "{@code file:}") to load a resource from a classpath file or
 * any other URL respectively.
 * <p>
 * Internally, this implementation uses the Spring {@link DefaultResourceLoader} class to load resources.
 *
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class ResourceLoader {

    private String source;
    private Resource resource;
    private String resourceContent;

    public ResourceLoader(String source) {
        Objects.requireNonNull(source);
        this.source = source;
        validateResource();
        resource = new DefaultResourceLoader().getResource(source);
        byte[] resourceData = getResourceData();
        resourceContent = new String(resourceData, Charset.defaultCharset());
    }

    public String load() {
        return resourceContent;
    }

    private void validateResource() {
        requireResourceExists();
        requireResourceReadable();
    }

    private void requireResourceReadable() {
        if (!resource.isReadable()) {
            throw new UnreadableResourceException(source);
        }
    }

    private void requireResourceExists() {
        if (!resource.exists()) {
            throw new ResourceNotFoundException(source);
        }
    }

    private byte[] getResourceData() {
        try {
            return ByteStreams.toByteArray(resource.getInputStream());
        } catch (IOException exception) {
            throw new IllegalStateException("Cannot read resource data", exception);
        }
    }

}
