package rocks.bastion.core

import org.junit.Test
import org.junit.runner.RunWith
import rocks.bastion.Bastion
import rocks.bastion.core.json.JsonResponseAssertions
import rocks.bastion.junit.BastionRunner

@RunWith(BastionRunner.class)
class GoogleBooksApiTest {

    @Test
    public void searchHarryPotter() {
        Bastion.request("Query 'Harry Potter'", GeneralRequest.get("https://www.googleapis.com/books/v1/volumes").addQueryParam("q", "harry potter"))
                .withAssertions(JsonResponseAssertions.fromFile(200, new File(GoogleBooksApiTest.class.getResource("/json/harry_potter_results.json").toURI())))
                .call()
    }

}
