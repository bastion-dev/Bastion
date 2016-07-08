package rocks.bastion.core

import org.junit.Test
import org.junit.runner.RunWith
import rocks.bastion.core.json.JsonRequest
import rocks.bastion.core.json.JsonResponseAssertions
import rocks.bastion.junit.BastionRunner
import rocks.bastion.support.embedded.TestWithEmbeddedServer

@RunWith(BastionRunner.class)
public class GroovyCreateSushiTest extends TestWithEmbeddedServer {

    @Test
    public void secondTestCreateSushi_Success() {
        Bastion.api("First Request", JsonRequest.postFromString("http://localhost:9876/sushi",
                '''{
                "name":"sashimi",
                "price":"5.60",
                "type":"SASHIMI"
            }'''
        )).withAssertions(JsonResponseAssertions.fromString(201,
                '''{
               "id":5,
               "name":"sashimi",
               "price":5.60,
               "type":"SASHIMI"
            }'''
        ).ignoreFieldsValues("/id")
        ).call()
    }
}