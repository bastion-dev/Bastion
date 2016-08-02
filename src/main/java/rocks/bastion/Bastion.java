package rocks.bastion;

import rocks.bastion.core.Assertions;
import rocks.bastion.core.BastionFactory;
import rocks.bastion.core.Callback;
import rocks.bastion.core.HttpRequest;
import rocks.bastion.core.builder.BastionBuilder;
import rocks.bastion.core.builder.ExecuteRequestBuilder;
import rocks.bastion.core.builder.PostExecutionBuilder;

/**
 * The main starting point for creating a Bastion test using the library.
 * <h1>Overview</h1>
 * <p>
 * Bastion is a library that eases the development of end-to-end tests for HTTP APIs. A typical user would write tests using
 * Bastion based off of some sort of API specification. This API specification can be anything like a WADL file, RAML file or a JSON schema.
 * A test engineer would prepare requests and responses based on these specifications to test the overall process of calling these APIs.
 * </p>
 * <p>
 * Bastion also contains tools that will help developers fix problems that occur as fast as possible. The JUnit 4 test runner,
 * {@link rocks.bastion.junit.BastionRunner}, for example, allows each individual HTTP request to appear in any test reports or UIs.
 * This is extremely useful as a developer will more easily understand which API or HTTP request failed when testing multi-step
 * processes.
 * </p>
 * <h1>Creating a Bastion Test</h1>
 * <p>
 * Bastion is run as part of your standard testing framework. If you are using JUnit 4 or above, we recommend also using the
 * {@link rocks.bastion.junit.BastionRunner} test runner for additional diagnostic information when a test fails. For an
 * example of a Bastion test, see the <a href="https://bastion.rocks">Bastion User Guide</a>.
 * </p>
 * <p>
 * Inside your test method or test case, make a call to the {@link Bastion#request(String, HttpRequest)} method. This will
 * return a <a href="https://en.wikipedia.org/wiki/Fluent_interface">fluent-builder style interface</a> that will allow you
 * to further specify the response model, assertions and callbacks on your test.
 * </p>
 * <p>
 * The returned builder will allow you call any of the following methods:
 * </p>
 * <ul>
 * <li>{@link rocks.bastion.core.builder.BindBuilder#bind(Class)}: Specify which class type to use when constructing a model of the received response. Bastion will
 * interpret and decode the received response object into an instance of whatever type is supplied to this method.</li>
 * <li>{@link rocks.bastion.core.builder.AssertionsBuilder#withAssertions(Assertions)}: Specify what test assertions to apply to the response. We recommend supplying the
 * assertions as a lambda or using one of the available subclass implementations of {@link Assertions}.</li>
 * <li>{@link rocks.bastion.core.builder.CallbackBuilder#thenDo(Callback)}: Specify a callback function to execute when the response is received and it passes its assertions. We
 * recommend supplying the {@link Callback} as a lambda function.</li>
 * <li>{@link ExecuteRequestBuilder#call()}: Starts the Bastion test by executing the HTTP request.</li>
 * </ul>
 * <p>
 * You cannot call any of the methods above before any of the methods listed before it. Therefore, in your test, you should call
 * the methods above one after each other as listed above: you can skip any of the methods and Bastion will assign defaults.
 * For example, if you want to make an HTTP request, apply some assertions without binding a model or using a callback, you would
 * call the {@link #request(String, HttpRequest)} method first, followed by the {@link rocks.bastion.core.builder.AssertionsBuilder#withAssertions(Assertions)}
 * method to configure your assertions, followed by the {@link ExecuteRequestBuilder#call()} method to send the request
 * and start the test.
 * </p>
 * <h1>Getting the Response</h1>
 * <p>
 * Once you execute the HTTP test using the {@link ExecuteRequestBuilder#call()} method, you can retrieve the HTTP response
 * and even any model object decoded by Bastion using the following methods:
 * </p>
 * <ul>
 * <li>{@link PostExecutionBuilder#getResponse()}: Get the HTTP response object that was returned following the request
 * sent with the Bastion test. This will contain the status code, and HTTP headers and the body content. It will also
 * contain the model object decoded by Bastion.</li>
 * <li>{@link PostExecutionBuilder#getModel()}: Get the model object decoded by Bastion from the HTTP response. This is
 * a convenience method instead of calling {@code .getResponse().getModel()}.</li>
 * </ul>
 * <p>
 * The model object is a plain Java object that was created after Bastion has decoded the content body. If you specified
 * a model type with the {@link rocks.bastion.core.builder.BindBuilder#bind(Class)} method, then the model object will be
 * of the type you specified. Otherwise, the type of model will be determined by Bastion depending on the content-type header
 * of the response. For example, if you did not use the {@link rocks.bastion.core.builder.BindBuilder#bind(Class)} method
 * and the response had content-type "application/json", then the model object will be of type {@link com.fasterxml.jackson.databind.JsonNode}.
 * </p>
 * <p>Obtaining the response in this way is useful especially if you want to perform another Bastion request after in the same
 * test. You can use the information retrieved in the model or response for the next Bastion request.</p>
 * <h1>Request Types</h1>
 * <p>
 * There are multiple types of requests that you can send using Bastion. To start building a Bastion test, construct and initialise
 * a request and pass it to the {@link #request(String, HttpRequest)} method. The types of {@link HttpRequest requests} you can
 * use are listed below (see each requests's JavaDocs for more information about how to use that request):
 * </p>
 * <ul>
 * <li>{@link rocks.bastion.core.GeneralRequest}: Allows you to specify a string content-body. Can also be used if you wouldn't
 * like to send any content-body (such as for a {@code GET} request).</li>
 * <li>{@link rocks.bastion.core.json.JsonRequest}: Allows you to specify a valid JSON string to send as part of your HTTP request
 * content-body.</li>
 * </ul>
 * <p>Also, feel free to provide your own implementation of an {@link HttpRequest} which will allow you customise completely
 * the request sent by Bastion. Implementing your own {@link HttpRequest request class} is extremely useful to encourage
 * reuse and maintainability (instead of repeating your own requests each time).</p>
 * <p>
 * <h1>Assertion Types</h1>
 * <p>
 * Assertions allow you to quickly check whether a test was successful or not. Note that a request does not necessarily have
 * to succeed for a test to pass. In fact, you are encouraged to test your error/failure paths extensively as well as your
 * success paths of your APIs to ensure good quality software. Bastion allows you to do this easily by supply an {@link Assertions}
 * object to the {@link rocks.bastion.core.builder.AssertionsBuilder#withAssertions(Assertions)}} method. At the moment, there
 * is only one built-in {@link Assertions assertion type} listed below (see the type's JavaDocs for more information on
 * how to use it):
 * </p>
 * <ul>
 * <li>{@link rocks.bastion.core.json.JsonResponseAssertions}: Expects a JSON response from the remote server. It allows
 * you to specify a valid JSON string to assert the response against. The assertion is smart in that a JSON structural comparison
 * is performed (instead of a straight-up text equality comparison) making sure that the response contains all the expected
 * properties, with the correct values, regardless of whitespace and property order.</li>
 * </ul>
 * <p>
 * Just like requests, you may define your own {@link Assertions assertion types}. You are encouraged to do so if you
 * are performing some sort of assertion frequently throughout your tests. This enables code reuse and improves the maintainability
 * of your tests.
 * </p>
 * <p>
 * If you do not want to implement your own assertion classes you can supply an assertions object directly into the
 * {@link rocks.bastion.core.builder.AssertionsBuilder#withAssertions(Assertions)} method of the Bastion test builder as
 * a Java 8 lambda.
 * </p>
 * <h1>Callbacks</h1>
 * <p>
 * {@link Callback Callback objects} can be provided for a Bastion test using the {@link rocks.bastion.core.builder.CallbackBuilder#thenDo(Callback)}
 * method. Callbacks are executed right after the test passes its assertions. Using a callback you may perform additional
 * post processing to the response object which is not necessarily related to test assertions. This can include logging,
 * setting variables for use further in your test and so on.
 * </p>
 * <p>You are encouraged to define your own {@link Callback callback} implementations. Just like requests and assertions,
 * this promotes code reuse and maintainability especially if you are performing an action multiple times across your
 * Bastion tests.</p>
 * <p>
 * If you do not want to implement your own callback classes though you can supply a callback object directly into the
 * {@link rocks.bastion.core.builder.CallbackBuilder#thenDo(Callback)} method of the Bastion test builder as a Java 8
 * lambda.
 * </p>
 * <h1>Groovy Tests</h1>
 * <p>
 * Certain features of Bastion such as the {@link rocks.bastion.core.json.JsonRequest} and the {@link rocks.bastion.core.json.JsonResponseAssertions}
 * classes work with strings. These string inputs will frequently turn out to be extremely long and, especially in Java,
 * this will cause your strings to appear unwieldy due to multiple breaklines and escape characters. Fortunately, Bastion
 * works with any of the JVM languages including Groovy.
 * </p>
 * <p>
 * Groovy is easy to set up for your project. After you have set up Groovy to run automated tests, simply add Bastion to
 * your project's dependencies (as explained above) and create your tests as normal. With Groovy, you can supply long strings
 * using alternate string delimiters including so-called multiline strings. These will improve the readability of your code
 * greatly when supplying strings to Bastion. See the <a href="https://bastion.rocks">Bastion User Guide</a>
 * for an example of how a Bastion test would look like in Groovy.
 * </p>
 */
public final class Bastion {

    /**
     * <p>
     * Starts building a single Bastion test which will execute the specified HTTP request. The first parameter is a descriptive
     * string that appears in test reports and any UI running your tests. The method will return a fluent-builder object which
     * will let you specify the test further.
     * </p>
     * <p>
     * The request you specify in this method can be an instance of one of the in-built {@link HttpRequest request types}
     * provided with Bastion itself. You can also supply your own implementation of a request by subclassing {@link HttpRequest}.
     * </p>
     *
     * @param message A descriptive message for this Bastion test.
     * @param request The HTTP request that Bastion will execute for this test.
     * @return A fluent-builder object which will let you bind a model type, add assertions, add callbacks and execute the test.
     */
    public static BastionBuilder<String> request(String message, HttpRequest request) {
        return BastionFactory.getDefaultBastionFactory().getBastion(message, request);
    }

    private Bastion() {
    }

}
