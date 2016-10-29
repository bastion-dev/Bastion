package rocks.bastion.core.printer;

import org.apache.http.entity.ContentType;
import org.junit.Test;
import rocks.bastion.core.ApiHeader;
import rocks.bastion.core.RawResponse;
import rocks.bastion.core.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class ResponsePrinterTest {
    @Test
    public void getAsString() throws Exception {
        ResponsePrinter printer = new ResponsePrinter(new RawResponse(HttpServletResponse.SC_CONFLICT, "Conflict",
                Arrays.asList(new ApiHeader("Header1", "value1"), new ApiHeader("Authorization", "token")),
                new ByteArrayInputStream(("{\n" +
                        "\t\"tkoast\":\"raskpor\",\n" +
                        "\t\"skoipra\":\"smroiar\"\n" +
                        "}").getBytes())));
        assertThat(printer.getAsString()).isEqualTo("HTTP/1.1 409 Conflict\r\n" +
                "Header1: value1\r\n" +
                "Authorization: token\r\n" +
                "\r\n" +
                "{\n" +
                "\t\"tkoast\":\"raskpor\",\n" +
                "\t\"skoipra\":\"smroiar\"\n" +
                "}");

    }
}