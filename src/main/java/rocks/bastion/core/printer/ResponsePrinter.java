package rocks.bastion.core.printer;

import com.google.common.io.CharStreams;
import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicLineFormatter;
import org.apache.http.message.BasicStatusLine;
import org.springframework.util.StreamUtils;
import rocks.bastion.core.Response;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Objects;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class ResponsePrinter {

    private Response response;

    public ResponsePrinter(Response response) {
        Objects.requireNonNull(response);
        this.response = response;
    }

    public void print(Writer writer) throws IOException {
        BasicLineFormatter formatter = new BasicLineFormatter();
        String statusLine = BasicLineFormatter.formatStatusLine(new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1),
                response.getStatusCode(), response.getStatusText()), formatter);
        writer.append(statusLine).append("\r\n");
        response.getHeaders().forEach(apiHeader -> {
            try {
                writer.append(BasicLineFormatter.formatHeader(new BasicHeader(apiHeader.getName(), apiHeader.getValue()), formatter)).append("\r\n");
            } catch (IOException exception) {
                throw new IllegalStateException(exception);
            }
        });
        writer.append("\r\n");
        InputStreamReader entity = new InputStreamReader(response.getBody());
        CharStreams.copy(entity, writer);
    }

    public String getAsString() {
        StringWriter writer = new StringWriter();
        try {
            print(writer);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not convert the HTTP request to a string", exception);
        }
        return writer.toString();
    }

}
