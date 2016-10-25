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

Bastion provides the following list of built-in `Request` subclasses:

* [GeneralRequest](#general-request): A simple HTTP request which allows for any arbitrary entity data.
* [JsonRequest](#json-request): An HTTP request which takes a JSON string as its entity data.
* [FormUrlEncodedRequest](#form-url-encoded-request): An HTTP request which takes data in the form of a map which is then sent as a form URL encoded request.

Any `Request` supports the following attributes, some of which are standard to HTTP:

* *Headers*:
* *Query Parameters*:
* *Route Parameters*:
* *Content Type*:
* *Entity Body*:

#### General Request

`GeneralRequest` is the universal HTTP request, able to take any arbitrary entity data string. To initialise a new `GeneralRequest` use any of the following
static factory methods, giving the URL you want to send the request on:

* `GeneralRequest.get()`: Initialise an HTTP `GET` request.
* `GeneralRequest.post()`: Initialise an HTTP `post()` request. This method also takes a string to use as the HTTP entity data (use `GeneralRequest.EMPTY_BODY` to send no data).
* `GeneralRequest.delete()`: Initialise an HTTP `delete()` request. This method also takes a string to use as the HTTP entity data (use `GeneralRequest.EMPTY_BODY` to send no data).
* `GeneralRequest.put()`: Initialise an HTTP `put()` request. This method also takes a string to use as the HTTP entity data (use `GeneralRequest.EMPTY_BODY` to send no data).
* `GeneralRequest.patch()`: Initialise an HTTP `patch()` request. This method also takes a string to use as the HTTP entity data (use `GeneralRequest.EMPTY_BODY` to send no data).

Calling any of the above methods will give you an initialised `GeneralRequest` object which can be used with `Bastion.request()`. The request will not initially
have any HTTP headers, query parameters or route parameters.

Once you have an instance of `GeneralRequest`, you can call methods to modify *Headers*, *Query Parameters*, *Route Parameters* and *Content type* as
explained in section [Requests](#requests).

[Examples using `GeneralRequest` go here]
 
#### JSON Request

`JsonRequest` is a request object specially designed to handle JSON data. Unlike `GeneralRequest`, `JsonRequest` will set the appropriate content type header
to indicate that the data being sent has mime-type `application/json`. The request object is initialised using a JSON string (or file) and will validate the 
given data to ensure that it is valid JSON (if you don't want this validation, use `GeneralRequest` instead). To initialise a new `JsonRequest` use any of the
following static factory methods, giving the URL you want to send the request on:

* `JsonRequest.fromString()`: Allows you to create a `JsonRequest` with the given HTTP method (`GET`, `POST`, etc.) and the given JSON string.
* `JsonRequest.fromResource()`: Allows you to create a `JsonRequest` with the given HTTP method. The JSON data to send is loaded from the given file or classpath resource.
* `JsonRequest.fromTemplate()`: Like `fromResource()` but this method will also take a map of template variable names to replacement values as keys and a Mustache template file. The template data is loaded and the variables replaced by the values in the given map. The resulting data is then used as the JSON entity for the request.

The factory methods above also have utility methods which do not take an `HttpMethod` argument as follows:

* `JsonRequest.postFromString()`
* `JsonRequest.postFromResource()`
* `JsonRequest.postfromTemplate()`
* `JsonRequest.putFromString()`
* `JsonRequest.putFromResource()`
* ... and so on.

The `JsonRequest.fromResource()` and `JsonRequest.fromTemplate()` family of factory methods take a string representing either a URL or even a classpath resource.
For example, to load request data from a fixture file in the classpath, you could do,

```java
Bastion.request(JsonRequest.postFromResource("http://localhost:8080/login", "classpath:/fixtures/login-request.json"));
```

#### Form URL Encoded Data Request

#### Custom Requests

### Assertions

_Assertions_ objects are passed to the `withAssertions()` method which is called either after the `request()` method or the `bind()` method when using the
`Bastion` builder. An `Assertions` objects defines the test predicate applied on the received HTTP response. If any of the applied assertions fail, then
the test fails. Certain `Assertions` objects will provide helpful messages and logs to explain how to transform the received response into the expected response.
When supplying `Assertions` using the `withAssertions()` method, you can use the `and()` method on the Assertions themselves to chain `Assertions` together.

We suggest using one of the built-in `Assertions` subclasses when defining your tests. Alternatively, if none of the built-in assertions subclasses are
useful, you can create your own `Assertions` subclass as explained in the section [Custom Assertions](#custom-assertions).

Bastion provides the following list of built-in `Assertions` subclasses.

* [JsonResponseAssertions](#json-response-assertions): Asserts that a received response is in JSON format and that the received response data is as expected.
* [JsonSchemaAssertions](#json-schema-assertions): Asserts that a received response is in JSON format and that the received response data at least conforms to the given JSON schema.

#### JSON Assertion

#### JSON Schema

#### Custom Assertions

### Binding a Model

### Executing and Getting Data

## Contribute

Bastion is an open-source project! Open-source means that we encourage you to contribute in any way you can. We will accept all contributions, in any shape
or form, that help make Bastion better. Here are some things you can do to contribute:

* Send a positive comment to the Bastion contributers. :)
* [Submit an issue](https://github.com/KPull/Bastion/issues) on GitHub containing a bug report or suggestion. We ask you to spend a couple minutes before
  submitting an issue to check that it has not been submitted earlier. When opening an issue, try to include as much detail as possible so that the
  community can more easily address your concern.
* Submit a pull request for any of our [open issues](https://github.com/KPull/Bastion/issues?q=is%3Aopen+is%3Aissue). Some issues are more easy to implement
  than others and, if you're just starting out, these issues let you get used to the Bastion code structure. If you need any assistance, simply comment on
  the issue at hand and we'll be glad to help. We ask that you adhere to a consistent code style and employ good programming practice but don't worry if
  you're unsure about anything: we'll help you get your submission up to scratch as well.
* You can also [submit a pull request](https://github.com/KPull/Bastion/pulls) which is not related to any of the issues currently on GitHub. If you have
  developed your own `Request` or `Assertions` implementations, for example, and you believe they could be useful to the rest of the Bastion community,
  we will add them to the library for use in future versions of Bastion.
* Make our User Guide better. Our User Guide is very important to us and we strive to keep it as up to date as possible. If you spot any omissions, typos,
  grammatical errors or have an idea of how it can be improved, please submit a pull request. The files for our user guide can be found in the `src/docs/md`
  directory.
* Spread the word. Tell your colleagues about Bastion or write a blog post about Bastion. The more people we can tell Bastion about, the better!