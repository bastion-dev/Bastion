# Overview

Bastion is a Java-based library for quickly creating tests for your HTTP APIs.
You can quickly define the request object to send as well as the expected response. Bastion excels at
running end-to-end tests for APIs which typically simulate a client's behaviour. If you execute multiple
requests using Bastion, for example, each request will be listed as its own test (inside surefire reports, for example)
to help you more easily isolate problems.

# Usage
## Depedency

Use a dependency management tool such as Maven to download the Bastion library for use in your project. Add the following
dependency to your POM file:
```xml
    <dependency>
        <groupId>rocks.bastion</groupId>
        <artifactId>bastion</artifactId>
        <version>0.1-SNAPSHOT</version>
        <scope>test</scope>
    <dependency>
```

## Test

The following is a simple test for a remote Sushi ordering service API:

```java
import org.junit.Test;
import org.junit.runner.RunWith;
import rocks.bastion.core.Bastion;
import rocks.bastion.core.json.*;
import rocks.bastion.junit.BastionRunner;
import rocks.bastion.support.embedded.*;

@RunWith(BastionRunner.class)
public class CreateSushiTest extends TestWithEmbeddedServer {
    @Test
    public void testCreateSushi_Success() {
        Bastion.request("First Request", JsonRequest.postFromString("http://localhost:9876/sushi", "{ " +
                "\"name\":\"sashimi\", " +
                "\"price\":\"5.60\", " +
                "\"type\":\"SASHIMI\" " +
                "}"
        )).withAssertions(JsonResponseAssertions.fromString(201, "{ " +
                        "\"id\":5, " +
                        "\"name\":\"sashimi\", " +
                        "\"price\":5.60, " +
                        "\"type\":\"SASHIMI\" " +
                        "}"
                ).ignoreFieldsValues("/id")
        ).call();
    }
}
```

The test above performs a single request. The first parameter to the `request()` method takes a descriptive name which will be used in test reports to indicate whether the request was successful or not.

The second argument takes a `Request` object: in our example, we supply a `JsonRequest` which is useful for sending HTTP requests containing JSON bodies. `JsonRequest.postFromString` will configure the request to use the `POST` HTTP method on the specified URL and also send the correct headers. Please see the JavaDocs for more information on how to configure different `JsonRequest`s.

The `withAssertions()` method adds assertions which are tested for when Bastion receives a response from the remote server. The assertions are supplied in the form of an `Assertions` object: in our example, `JsonResponseAssertions` tests the actual received response against the supplied HTTP status code and JSON content. This form of assertions is smart enough to ignore insignificant differences in the response (whitespace, different ordering of properties, etc) and will output a JSON patch explain how to turn the actual response into an expected one. We use the `ignoreFieldsValues()` methods to ignore the *value* of received id. The assertion will still make sure that the field `id` was returned by the response but will ignore the value returned.

Finally, `call()` executes the request and performs the assertions on the response. Notice that, you can supply your own implementations of `Request` and `Assertions` to suit your exact testing needs.

## Using Callbacks

# Building

Use Maven to build Bastion and run the associated tests. After checking out the repository 
use the following command to build and test the source code.

    mvn install

# Contributing

Bastion is an open-source project which means that we will gladly accept contributions. Simply 
fork the repository and submit pull requests for review. Do use standard good coding practice 
in your submissions and read the [roadmap](https://github.com/KPull/Bastion/wiki/Roadmap) which 
is an indication of what we really need next.
