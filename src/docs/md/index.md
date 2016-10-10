# Bastion User Guide

[TOC]

## Introduction

Bastion is a Java-based library for testing HTTP APIs and endpoints. Developers can use Bastion to test any type of HTTP service
but the library also provides built-in classes for testing JSON endpoints and validating JSON responses.

This reference manual explains all the features of the Bastion library and how to use them. For a more detailed description of each
the Bastion API, please see the [Bastion JavaDocs](http://bastion.rocks/javadocs/index.html).

## Getting Started

### Maven Dependency

Using Bastion for your project is easily done by using a dependency management tool. Bastion is available on the Maven Central repository.
For Java projects with a `pom.xml` file, you can add the following dependency to your `<dependencies>` section to download Bastion and make
it available for your project's tests.

```xml
<dependency>
    <groupId>rocks.bastion</groupId>
    <artifactId>bastion</artifactId>
    <version>[VERSION]</version>
    <scope>test</scope>
</dependency>
```

### Groovy Grapes

If you are using Groovy scripts for your tests, you can easily download Bastion directly from within your script files, using Grape. Add
the following line to your `import` statement to download Bastion and make it available to your script.

```groovy
@Grapes(
    @Grab(group='rocks.bastion', module='bastion', version='[VERSION]')
)
import rocks.bastion.Bastion
```

### Writing your first Bastion test

Bastion makes it very easy to write simple API tests. Let us look at a test for an API which returns JSON.
  
```java
@RunWith(BastionRunner.class)
public class VatValidatorTest {
    @Test
    public void callValidateVatNumber_returnsAmazonVatData() {
        Bastion.request("Validate Amazon's VAT Number", GeneralRequest.get("http://vatlayer.com/php_helper_scripts/vat_api.php")
                .addQueryParam("secret_key", "577b6fb6551df7f3532ecbd45ea07ddd")
                .addQueryParam("vat_number", "LU26375245")
        ).withAssertions(JsonResponseAssertions.fromResource(200, "classpath:/json/amazon_vat.json")).call();
    }
}
```

In this test, we make a simple `GET` request on the URL `http://vatlayer.com/php_helper_scripts/vat_api.php`. We add two query parameters
to the request namely, `secret_key` and `vat_number`. We then expect a JSON response using a `JsonResponseAssertions` object. This assertions
object will check that the status code is `200` (as specified by the user), and that the content-type header returned is indeed `application/json`.
It will also check that the returned JSON content matches that found in the `json/amazon_vat.json` file.

Notice that this JSON comparison is not a straight-up text equality comparison: instead, it structurally compares the actual received JSON to
the expected JSON as if they were objects. Changing the order of properties in the response won't matter. Even better, say the test fails, Bastion
will tell you exactly what operations you need to perform to the response to get the expected outcome. Here is an example of an error output when
the `JsonResponseAssertions` fail:

```
java.lang.AssertionError: Actual response body is not as expected. The following JSON Patch (as per RFC-6902) tells you what 
operations you need to perform to transform the actual response body into the expected response body:
  [{"op":"replace","path":"/country_code","value":"MT"}]
```

We can see how the error tells us to change the value in the `/country_code` property to `MT`.

This is only a taste of what Bastion can do. In the sections below, we explain all the features of Bastion and how you can even extend the library
to create your own requests and assertions depending on your domain.

## Features

### The Structure of a Bastion Test

Bastion tests are implemented using the `Bastion` builder. This class provides a fluent-like interface for specifying tests. In the code snippet
below, we show how all the methods of the builder would look like when executed together.

```java
Bastion.request(...)
       .bind(...)
       .withAssertions(...)
       .thenDo(...)
       .call()
       .getModel()
```

This shows the basic structure of a Bastion test. Each one of the methods listed above (except `request()` and `call()`) is optional but they still
must be specified in the order above. If you chose to skip `withAssertions()`, for example, you must call `bind()` before `call()`. The list below
explains each one of these methods separately.

* `request()`: This method is the main entry-point to create a Bastion test. You must specify a name which will identify this test in test reports
and also provide a _request_ object that tells Bastion what kind of HTTP request to send. Bastion provides a number of different
built-in requests you can use (eg. `JsonRequest`) but you can also implement your own request types. For a list of all built-in requests see 
[Requests](#requests). The [Custom Requests](#custom-requests) section explains how to implement your own requests.
* `bind()`: Tells Bastion which model class to use when interpreting the incoming HTTP entity. When Bastion receives a response from the remote
server, it will decode the received entity data into an object of the given type. If this decoding process fails for some reason,
the entire test is marked as failed. By providing a model class using the `bind()` method, you'll have this type information available
for later on when calling the `withAssertions()`, `thenDo()`, `getModel()` and `getResponse().getModel()` methods.
* `withAssertions()`: Takes an _assertions_ object which will verify that the response returned by the remote server is correct.
Bastion provides a number of different built-in assertion objects for common verifications you might want to do (eg. `JsonResponseAssertions`) but you can also
implement your own assertions. For a list of all built-in assertions see [Assertions](#assertions). The 
[Custom Assertions](#custom-assertions) section explains how to implement your own assertions.
* `thenDo()`: Takes a _Callback_ object which will be executed when the response is received and the assertions pass. This is useful if you would
like to perform specific actions after Bastion requests. This could be logging the response, for example, or saving authentication data for future
Bastion requests.
* `call()`: Executes the API request configured with the previous commands. Any assertions will be applied on the received response. The call operation
will fail if Bastion is unable to bind the received response to a model or the assertions fail.
* `getResponse()`: After the `call()` method is executed, you can get the HTTP response object received using the `getResponse()` method. The returned
response object will contain the bound model obtained from the response data.
* `getModel()`: After the `call()` method is executed, you can get the bound model obtained from the response data.

### Requests

_Request_ objects are passed to the `request()` method which is the first builder method invoked when using the `Bastion` builder. A `Request` object
defines the HTTP data that is sent to the remote server while the test is executing. We suggest using one of the built-in `Request` subclasses when
supplying your request data. Alternatively, if none of the built-in request subclasses are useful, you can create your own `Request` subclass
as explained in the section [Custom Requests](#custom-requests).

Bastion provides the following list of in-built `Request` subclasses:

* [GeneralRequest](#general-request): A simple HTTP request which allows for any arbitrary entity data.
* [JsonRequest](#json-request): An HTTP request which takes a JSON string as its entity data.
* [FormUrlEncodedRequest](#form-url-encoded-request): An HTTP request which takes data in the form of a map which is then sent as a form URL encoded request.

#### General Request

#### JSON Request

#### Form URL Encoded Data Request

#### Custom Requests

### Assertions

#### JSON Assertion

#### JSON Schema

#### Custom Assertions

## Contribute