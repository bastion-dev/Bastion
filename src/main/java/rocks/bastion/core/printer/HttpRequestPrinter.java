package rocks.bastion.core.printer;

import com.mashape.unirest.http.HttpMethod;
import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicLineFormatter;
import org.apache.http.message.BasicRequestLine;
import org.apache.http.message.LineFormatter;
import rocks.bastion.core.HttpRequest;
import rocks.bastion.core.RequestExecutor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Objects;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class HttpRequestPrinter {

    public HttpRequest request;

    public HttpRequestPrinter(HttpRequest request) {
        Objects.requireNonNull(request);
        this.request = request;
    }

    public void print(Writer writer) throws IOException {
        RequestExecutor executor = new RequestExecutor(request);
        URL url = new URL(executor.getResolvedUrl());
        BasicRequestLine requestLine = new BasicRequestLine(executor.getMethod(), url.getFile(), new ProtocolVersion("HTTP", 1, 1));
        BasicLineFormatter formatter = new BasicLineFormatter();
        writer.append(BasicLineFormatter.formatRequestLine(requestLine, formatter)).append("\r\n");
        writer.append(BasicLineFormatter.formatHeader(new BasicHeader("Host", url.getHost()), formatter)).append("\r\n");
        executor.getHeaders().forEach(apiHeader -> {
            try {
                writer.append(BasicLineFormatter.formatHeader(new BasicHeader(apiHeader.getName(), apiHeader.getValue()), formatter)).append("\r\n");
            } catch (IOException exception) {
                throw new IllegalStateException(exception);
            }
        });
        writer.append("\r\n");
        writer.append(request.body().toString());
    }

    public String getAsString() {
        StringWriter writer = new StringWriter();
        try {
            print(writer);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not conver the HTTP request to a string", exception);
        }
        return writer.toString();
    }

}
