# Overview

Bastion is a library that eases the development of end-to-end tests for HTTP APIs. A typical user would write tests using Bastion based off of some sort of API specification. This API specification can be anything like a WADL file, RAML file or a JSON schema. A test engineer would prepare requests and responses based on these specifications to test the overall process of calling these APIs.

Bastion also contains tools that will help developers fix problems that occur as fast as possible. The JUnit 4 test runner, BastionRunner, for example, allows each individual HTTP request to appear in any test reports or UIs. This is extremely useful as a developer will more easily understand which API or HTTP request failed when testing multi-step processes. 

# Usage

## Depedency

Use a dependency management tool such as Maven to download the Bastion library for use in your project. Add the following
dependency to your POM file:
```xml
    <dependency>
        <groupId>rocks.bastion</groupId>
        <artifactId>bastion</artifactId>
        <version>0.2.1-BETA</version>
        <scope>test</scope>
    <dependency>
```

## Test

The following is a simple test for a remote Sushi ordering service API (imports omitted):

```java
@RunWith(BastionRunner.class)
public class CreateSushiTest extends TestWithEmbeddedServer {
    @Test
    public void testCreateSushi_Success() {
        Sushi createdSushi = Bastion.request("First Request", JsonRequest.postFromString("http://localhost:9876/sushi", "{ " +
                "\"name\":\"sashimi\", " +
                "\"price\":\"5.60\", " +
                "\"type\":\"SASHIMI\" " +
                "}"
        )).bind(Sushi.class).withAssertions(JsonResponseAssertions.fromString(201, "{ " +
                        "\"id\":5, " +
                        "\"name\":\"sashimi\", " +
                        "\"price\":5.60, " +
                        "\"type\":\"SASHIMI\" " +
                        "}"
                ).ignoreFieldsValues("/id")
        ).call().getModel();
    }
}
```

The test above performs a single request. Notice that we use the `BastionRunner` class as our test runner (using the `@RunWith` annotation) which causes each API request to show up in your IDE's test window.  The first parameter to the `request()` method takes a descriptive name which will be used in test reports to indicate whether the request was successful or not.

The second argument takes a `Request` object: in our example, we supply a `JsonRequest` which is useful for sending HTTP requests containing JSON bodies. `JsonRequest.postFromString` will configure the request to use the `POST` HTTP method on the specified URL and also send the correct headers. Please see the JavaDocs for more information on how to configure different `JsonRequest`s.

The `bind()` method will cause Bastion to map the response into an object of type `Sushi`. Bastion will use its converters and decoders to interpret the response (in this case, the response will be in JSON) and initialise a Java object of the specified type. If Bastion is unable to do so, the test fails.

The `withAssertions()` method adds assertions which are tested for when Bastion receives a response from the remote server. The assertions are supplied in the form of an `Assertions` object: in our example, `JsonResponseAssertions` tests the actual received response against the supplied HTTP status code and JSON content. This form of assertions is smart enough to ignore insignificant differences in the response (whitespace, different ordering of properties, etc) and will output a JSON patch explaining how to turn the actual response into an expected one. We use the `ignoreFieldsValues()` methods to ignore the *value* of received id. The assertion will still make sure that the field `id` was returned by the response but will ignore the value returned.

Finally, `call()` executes the request and performs the assertions on the response. Notice that, you can supply your own implementations of `Request` and `Assertions` to suit your exact testing needs. If you would like to use the returned body content or any of the response information use the methods `getModel()` and `getResponse()`.

Always refer to the JavaDocs for more information on how to build Bastion tests, how to use the in-built Request and Assertion classes and more!

## Groovy

We can see from the example above that the long strings given to the request and assertions objects are unwieldy. The escape characters and newlines make the test unpleasant to look at and read. Fortunately, Bastion works with any of the JVM languages, including Groovy!

Groovy is easy to set up for your project. After you have set up Groovy to run automated tests, simply add Bastion to your project's dependencies (as explained above) and create your tests as normal. With Groovy, you can supply long strings using alternate string delimiters including so-called multiline strings. These will improve the readability of your code greatly when supplying strings to Bastion. You can see how the previous test would look like in Groovy, below (imports omitted):

```groovy
@RunWith(BastionRunner.class)
public class GroovyCreateSushiTest extends TestWithEmbeddedServer {
    @Test
    public void secondTestCreateSushi_Success() {
        Bastion.request("First Request", JsonRequest.postFromString("http://localhost:9876/sushi",
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
            ).ignoreValuesForProperties("/id")
        ).call()
    }
}
```

# Building

Use Maven to build Bastion and run the associated tests. After checking out the repository 
use the following command to build and test the source code.

    mvn install

# Contributing

Bastion is an open-source project which means that we will gladly accept contributions. Simply 
fork the repository and submit pull requests for review. Do use standard good coding practice 
in your submissions and read the [roadmap](https://github.com/KPull/Bastion/wiki/Roadmap) which 
is an indication of what we really need next.
