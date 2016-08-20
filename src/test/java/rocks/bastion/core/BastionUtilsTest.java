package rocks.bastion.core;

import com.google.common.collect.Lists;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class BastionUtilsTest {

    @Test
    public void propertiesToNameValuePairs() throws Exception {
        List<NameValuePair> nameValuePairs = BastionUtils.propertiesToNameValuePairs(Lists.newArrayList(new ApiProperty("name", "john"), new ApiProperty("surname", "doe")));
        assertThat(nameValuePairs).describedAs("Converted Pair Objects").containsExactly(new BasicNameValuePair("name", "john"), new BasicNameValuePair("surname", "doe"));
    }

    @Test
    public void propertiesToNameValuePairs_empty() throws Exception {
        List<NameValuePair> nameValuePairs = BastionUtils.propertiesToNameValuePairs(Lists.newArrayList());
        assertThat(nameValuePairs).describedAs("Converted Pair Objects").isEmpty();
    }

    @Test(expected = NullPointerException.class)
    public void propertiesToNameValuePairs_null_exceptionShouldBeThrown() throws Exception {
        BastionUtils.propertiesToNameValuePairs(null);
    }

    @Test
    public void propertyToNameValuePair() throws Exception {
        BasicNameValuePair nameValuePair = BastionUtils.propertyToNameValuePair(new ApiProperty("name", "john"));
        assertThat(nameValuePair).describedAs("Converted Pair Object").isEqualTo(new BasicNameValuePair("name", "john"));
    }

    @Test(expected = NullPointerException.class)
    public void propertyToNameValuePair_null_exceptionShouldBeThrown() throws Exception {
        BastionUtils.propertyToNameValuePair(null);
    }

}