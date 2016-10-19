# Overview

Bastion is a library that eases the development of end-to-end tests for HTTP APIs. A typical user would write 
tests using Bastion based off of some sort of API specification. This API specification can be anything like a 
WADL file, RAML file or a JSON schema. A test engineer would prepare requests and responses based on these specifications 
to test the overall process of calling these APIs.

# Usage

## Test

* `GET` Request:
[ex:general-get-example]

* `POST` Request:
[ex:general-post-example]

* JSON Assertion: (property order in response does not affect test)
[ex:simple-json-assertion]

* JSON Request:
[ex:json-request-example]

* JSON Request/Assertion loaded from file:
[ex:load-from-file]

* Form URL Encoded Data Request:
[ex:form-url-encoded-request]

* Bind the response entity to a model object:
[ex:bound-data] 

## Dependency

Use a dependency management tool such as Maven to download the Bastion library for use in your project. Add the following
dependency to your POM file:
```xml
<dependency>
    <groupId>rocks.bastion</groupId>
    <artifactId>bastion</artifactId>
    <version>[VERSION]</version>
    <scope>test</scope>
<dependency>
```

Alternatively, use Groovy Grapes to use Bastion in your Groovy tests/scripts:
```groovy
@Grapes(
    @Grab(group='rocks.bastion', module='bastion', version='[VERSION]')
)
```

# Building

Use Maven to build Bastion and run the associated tests. After checking out the repository 
use the following command to build and test the source code.

    mvn install

# Contributing

Bastion is an open-source project which means that we will gladly accept contributions. Feel free
to submit issues or suggestions on Github. We will also accept any code contributions to the project.
Simply fork the repository and submit pull requests for review. Do use standard good coding practice 
in your submissions. Also, you can look at our 'Issues' page to get an idea of what you can work on.
