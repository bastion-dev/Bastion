package rocks.bastion.core.printer;

import com.google.common.io.CharStreams;
import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicLineFormatter;
import org.apache.http.message.BasicStatusLine;
import rocks.bastion.core.Response;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Objects;

/**
 * Prints the given {@link Response} object in the same format that it is sent with using the HTTP protocol. This class is
 * useful for logging and debugging purposes.
 */
public class ResponsePrinter {

    private Response response;

    /**
     * Constructs a new instance of this printer to format the specified response object.
     *
     * @param response The response object to format. Cannot be {@literal null}.
     */
    public ResponsePrinter(Response response) {
        Objects.requireNonNull(response);
        this.response = response;
    }

    /**
     * Formats and prints the response object to the specified {@link Writer} stream. The format used when outputting the
     * response conforms to the HTTP standard.
     *
     * @param writer The writer to output to. Cannot be {@link null}.
     * @throws IOException Thrown if the response could not be formatted and sent to the specified writer.
     */
    public void print(Writer writer) throws IOException {
        Objects.requireNonNull(writer);
        writeHeadSection(writer);
        writeEntitySection(writer);
    }

    /**
     * Formats and returns the response object. The format produced by this method for the response conforms to the HTTP
     * standard.
     *
     * @return A string representation of the HTTP data received
     */
    public String getAsString() {
        StringWriter writer = new StringWriter();
        try {
            print(writer);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not convert the HTTP request to a string", exception);
        }
        return writer.toString();
    }

    private void writeHeadSection(Writer writer) throws IOException {
        BasicLineFormatter formatter = new BasicLineFormatter();
        writeStatusLine(writer, formatter);
        writeHeaders(writer, formatter);
        writer.append("\r\n");
    }

    private void writeHeaders(Writer writer, BasicLineFormatter formatter) {
        response.getHeaders().forEach(apiHeader -> {
            try {
                writer.append(BasicLineFormatter.formatHeader(new BasicHeader(apiHeader.getName(), apiHeader.getValue()), formatter)).append("\r\n");
            } catch (IOException exception) {
                throw new IllegalStateException(exception);
            }
        });
    }

    private void writeStatusLine(Writer writer, BasicLineFormatter formatter) throws IOException {
        String statusLine = BasicLineFormatter.formatStatusLine(new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1),
                response.getStatusCode(), response.getStatusText()), formatter);
        writer.append(statusLine).append("\r\n");
    }

    private void writeEntitySection(Writer writer) throws IOException {
        InputStreamReader entity = new InputStreamReader(response.getBody());
        CharStreams.copy(entity, writer);
    }

}
