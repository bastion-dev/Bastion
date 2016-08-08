package rocks.bastion.core.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.junit.Assert;
import rocks.bastion.core.Assertions;
import rocks.bastion.core.ModelResponse;
import rocks.bastion.core.resource.ResourceLoader;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Asserts that an API response has conforms to a given JSON schema.
 */
public class JsonSchemaAssertions implements Assertions<Object> {

    final String expectedSchema;

    public JsonSchemaAssertions(final String expectedSchema) {
        Objects.requireNonNull(expectedSchema);
        this.expectedSchema = expectedSchema;
    }

    public static JsonSchemaAssertions fromString(final String expectedSchemaJson) {
        return new JsonSchemaAssertions(expectedSchemaJson);
    }

    public static JsonSchemaAssertions fromResource(final String expectedSchemaSource) {
        Objects.requireNonNull(expectedSchemaSource);
        return new JsonSchemaAssertions(new ResourceLoader(expectedSchemaSource).load());
    }

    @Override
    public void execute(final int statusCode,
                        final ModelResponse<?> response,
                        final Object model) throws AssertionError {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonFactory factory = mapper.getFactory();
            JsonNode actualJsonTree = factory.createParser(response.getBody()).readValueAsTree();

            final ProcessingReport validationReport = JsonSchemaFactory.byDefault()
                                                               .getJsonSchema(mapper.readTree(expectedSchema)).validate(actualJsonTree);
            if (!validationReport.isSuccess()) {
                final String messages = StreamSupport.stream(validationReport.spliterator(), false)
                                                    .map(ProcessingMessage::getMessage)
                                                    .collect(Collectors.joining(", "));
                Assert.fail(String.format("Actual response body is not as specified. The following message(s) where produced during validation; %s.", messages));
            }
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while parsing JSON text", e);
        } catch (ProcessingException e) {
            throw new RuntimeException("An error occurred while obtaining JSON schema", e);
        }
    }

}
