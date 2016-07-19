package rocks.bastion.support;

import org.junit.Test;
import org.junit.runner.RunWith;
import rocks.bastion.core.Bastion;
import rocks.bastion.core.GeneralRequest;
import rocks.bastion.core.json.JsonRequest;
import rocks.bastion.core.json.JsonResponseAssertions;
import rocks.bastion.junit.BastionRunner;
import rocks.bastion.support.embedded.Sushi;
import rocks.bastion.support.embedded.TestWithEmbeddedServer;

import java.io.File;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ChiaraFSC on 15/07/2016.
 */
@RunWith(BastionRunner.class)
public class CreateAndGetSushiFromFileTest extends TestWithEmbeddedServer {

    private final static String BASE_URL = "http://localhost:9876/sushi";

    @Test
    public void createAndGetSameSushi_Success() throws URISyntaxException {
        File requestFile = new File(CreateAndGetSushiFromFileTest.class.getResource("/json/create_sushi_request.json").toURI());
        File responseFile = new File(CreateAndGetSushiFromFileTest.class.getResource("/json/create_sushi_response.json").toURI());

        Sushi createdSushi = Bastion.request("Create Sushi", JsonRequest.postFromFile(BASE_URL, requestFile))
                                    .bind(Sushi.class)
                                    .withAssertions(JsonResponseAssertions.fromFile(201, responseFile).ignoreValuesForProperties("/id"))
                                    .call()
                                    .getModel();

        Sushi gottenSushi = Bastion.request("Get Sushi", GeneralRequest.get(BASE_URL + "/" + createdSushi.getId()))
                                   .bind(Sushi.class)
                                   .withAssertions(JsonResponseAssertions.fromFile(200, responseFile).ignoreValuesForProperties("/id"))
                                   .call()
                                   .getModel();

        assertThat(gottenSushi.getId()).isEqualTo(createdSushi.getId());
    }
}