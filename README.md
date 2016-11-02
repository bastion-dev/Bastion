# Overview

Bastion is a library that eases the development of end-to-end tests for HTTP APIs. A typical user would write 
tests using Bastion based off of some sort of API specification. This API specification can be anything like a 
WADL file, RAML file or a JSON schema. A test engineer would prepare requests and responses based on these specifications 
to test the overall process of calling these APIs.

# Usage

## Test

* `GET` Request:
```java
Bastion.request("Get the Restaurant's Name", GeneralRequest.get("http://localhost:9876/restaurant"))
        .withAssertions((statusCode, response, model) -> assertThat(model).isEqualTo("The Sushi Parlour"))
        .call();
```

* `POST` Request:
```java
Bastion.request("Change the Restaurant's Name", GeneralRequest.post("http://localhost:9876/restaurant", "The Fish Parlour"))
        .withAssertions((statusCode, response, model) -> assertThat(model).isEqualTo("The Fish Parlour"))
        .call();
```

* JSON Assertion: (property order in response does not affect test)
```java
Bastion.request("Get Nigiri Info", GeneralRequest.get("http://localhost:9876/nigiri"))
        .withAssertions(JsonResponseAssertions.fromString(200, "{ \"id\":5, \"name\":\"Salmon Nigiri\", \"price\":23.55 }"))
        .call();
```

* JSON Request:
```java
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
        ).ignoreValuesForProperties("/id")
).call();
```

* JSON Request/Assertion loaded from file:
```java
Bastion.request("Create Sushi", JsonRequest.postFromResource(BASE_URL, "classpath:/json/create_sushi_request.json"))
        .withAssertions(JsonResponseAssertions.fromResource(201, "classpath:/json/create_sushi_response.json").ignoreValuesForProperties("/id"))
        .call();
```

* Form URL Encoded Data Request:
```java
Bastion.request("Order Sashimi", FormUrlEncodedRequest.post("http://localhost:9876/sashimi")
        .addDataParameter("quantity", "5")
        .addDataParameter("table", "61")
).withAssertions(JsonResponseAssertions.fromString(200, "{ \"id\":5, \"name\":\"Sashimi\", \"price\":5.95 }"))
        .call();
```

* Bind the response entity to a model object:
```java
Sushi createdSushi = Bastion.request("Create Sushi", JsonRequest.postFromString("http://localhost:9876/sushi", "{ " +
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
        ).ignoreValuesForProperties("id")
).call().getModel();
```

* Groovy (using multi-line strings):
```java
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
```

## Dependency

Use a dependency management tool such as Maven to download the Bastion library for use in your project. Add the following
dependency to your POM file:
```xml
<dependency>
    <groupId>rocks.bastion</groupId>
    <artifactId>bastion</artifactId>
    <version>0.3-SNAPSHOT</version>
    <scope>test</scope>
</dependency>
```

Alternatively, use Groovy Grapes to use Bastion in your Groovy tests/scripts:
```groovy
@Grapes(
    @Grab(group='rocks.bastion', module='bastion', version='0.3-SNAPSHOT')
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
