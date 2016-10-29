package rocks.bastion.core.printer;

import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicLineFormatter;
import org.apache.http.message.BasicRequestLine;
import rocks.bastion.core.ApiHeader;
import rocks.bastion.core.HttpRequest;
import rocks.bastion.core.RequestExecutor;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Collection;
import java.util.Objects;

/**
 * Prints the given {@link HttpRequest} object in the same format that it is sent with using the HTTP protocol. This class is
 * useful for logging and debugging purposes.
 */
public class HttpRequestPrinter {

    private HttpRequest request;

    /**
     * Constructs a new instance of this printer to format the specified request object.
     *
     * @param request The request object to format. Cannot be {@literal null}.
     */
    public HttpRequestPrinter(HttpRequest request) {
        Objects.requireNonNull(request);
        this.request = request;
    }

    /**
     * Formats and prints the request object to the specified {@link Writer} stream. The format used when outputting the
     * response conforms to the HTTP standard.
     *
     * @param writer The writer to output to. Cannot be {@link null}.
     * @throws IOException Thrown if the request could not be formatted and sent to the specified writer.
     */
    public void print(Writer writer) throws IOException {
        Objects.requireNonNull(writer);
        writeHeadSection(writer);
        writeEntitySection(writer);
    }

    /**
     * Formats and returns the request object. The format produced by this method for the request conforms to the HTTP
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
        RequestExecutor executor = new RequestExecutor(request);
        URL url = new URL(executor.getResolvedUrl());
        BasicLineFormatter formatter = new BasicLineFormatter();
        writeRequestLine(url, writer, formatter);
        writeHeaders(url, executor.getHeaders(), writer, formatter);
        writer.append("\r\n");
    }

    private void writeHeaders(URL url, Collection<ApiHeader> headers, Writer writer, BasicLineFormatter formatter) throws IOException {
        writer.append(BasicLineFormatter.formatHeader(new BasicHeader("Host", url.getHost()), formatter)).append("\r\n");
        headers.forEach(apiHeader -> {
            try {
                writer.append(BasicLineFormatter.formatHeader(new BasicHeader(apiHeader.getName(), apiHeader.getValue()), formatter)).append("\r\n");
            } catch (IOException exception) {
                throw new IllegalStateException(exception);
            }
        });
    }

    private void writeRequestLine(URL url, Writer writer, BasicLineFormatter formatter) throws IOException {
        BasicRequestLine requestLine = new BasicRequestLine(request.method().getValue(), url.getFile(), new ProtocolVersion("HTTP", 1, 1));
        writer.append(BasicLineFormatter.formatRequestLine(requestLine, formatter)).append("\r\n");
    }

    private void writeEntitySection(Writer writer) throws IOException {
        writer.append(request.body().toString());
    }

}
