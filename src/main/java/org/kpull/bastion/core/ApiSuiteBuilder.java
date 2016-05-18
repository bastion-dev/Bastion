package org.kpull.bastion.core;

import org.apache.commons.io.FileUtils;
import org.jglue.fluentjson.JsonArrayBuilder;
import org.jglue.fluentjson.JsonBuilderFactory;
import org.jglue.fluentjson.JsonObjectBuilder;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

import static java.lang.String.format;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class ApiSuiteBuilder {

    private String name = "";
    private ApiEnvironment environment = new ApiEnvironment();
    private List<ApiCall> apiCalls = new LinkedList<>();
    private ApiSuiteBuilder() {
    }

    public static ApiSuiteBuilder start() {
        return new ApiSuiteBuilder();
    }

    public ApiSuiteBuilder name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }

    public ApiEnvironmentBuilder environment() {
        return new ApiEnvironmentBuilder();
    }

    public ApiCallBuilder call() {
        return new ApiCallBuilder();
    }

    public ApiSuite build() {
        return new ApiSuite(name, environment, apiCalls);
    }

    public class ApiEnvironmentBuilder {
        private Map<String, String> entries = new HashMap<>();

        private ApiEnvironmentBuilder() {
        }

        public ApiEnvironmentBuilder entry(String key, Object value) {
            Objects.requireNonNull(key);
            Objects.requireNonNull(value);
            entries.put(key, value.toString());
            return this;
        }

        public ApiSuiteBuilder done() {
            environment.putAll(entries);
            return ApiSuiteBuilder.this;
        }
    }

    public class ApiCallBuilder {
        private String name = "";
        private String description = "";
        private ApiRequest request = new ApiRequest("", "", Collections.emptyList(), "", "", Collections.emptyList());
        private ApiResponse response = new ApiResponse(Collections.emptyList(), 0, "", "");
        private Class<?> responseModel = null;
        private Assertions<?> assertions = null;
        private Callback postCallExecution = Callback.NO_OPERATION_CALLBACK;

        private ApiCallBuilder() {
        }

        public ApiCallBuilder name(String name) {
            Objects.requireNonNull(name);
            this.name = name;
            return this;
        }

        public ApiCallBuilder description(String description) {
            Objects.requireNonNull(description);
            this.description = description;
            return this;
        }

        public PostCallScriptBuilder afterwardsExecute() {
            return new PostCallScriptBuilder();
        }

        public <M> ResponseModelBuilder<M> responseModel(Class<M> responseModel) {
            Objects.requireNonNull(responseModel);
            this.responseModel = responseModel;
            return new ResponseModelBuilder<>();
        }

        public ApiRequestBuilder request() {
            return new ApiRequestBuilder();
        }

        public ApiResponseBuilder response() {
            return new ApiResponseBuilder();
        }

        public ApiSuiteBuilder done() {
            apiCalls.add(new ApiCall(name, description, request, response, responseModel, assertions, postCallExecution));
            return ApiSuiteBuilder.this;
        }

        public class PostCallScriptBuilder {

            public ApiCallBuilder nothing() {
                postCallExecution = Callback.NO_OPERATION_CALLBACK;
                return ApiCallBuilder.this;
            }

            public ApiCallBuilder groovy(String groovyScript) {
                Objects.requireNonNull(groovyScript);
                postCallExecution = new GroovyCallback(groovyScript);
                return ApiCallBuilder.this;
            }

            public ApiCallBuilder groovyFromFile(File groovyFile) {
                try {
                    Objects.requireNonNull(groovyFile);
                    postCallExecution = new GroovyCallback(FileUtils.readFileToString(groovyFile));
                    return ApiCallBuilder.this;
                } catch (IOException e) {
                    throw new IllegalStateException(format("Cannot open file: %s", groovyFile), e);
                }
            }

            public ApiCallBuilder callback(Callback callback) {
                Objects.requireNonNull(callback);
                ApiCallBuilder.this.postCallExecution = callback;
                return ApiCallBuilder.this;
            }
        }

        public class ResponseModelBuilder<M> {

            public ApiCallBuilder assertions(Assertions<M> assertions) {
                Objects.requireNonNull(assertions);
                ApiCallBuilder.this.assertions = assertions;
                return ApiCallBuilder.this;
            }

            public ApiCallBuilder noAssertions() {
                return ApiCallBuilder.this;
            }

        }

        public class ApiRequestBuilder {
            private String method = "";
            private String url = "";
            private List<ApiHeader> headers = new LinkedList<>();
            private List<ApiQueryParam> queryParams = new LinkedList<>();
            private String type = "";
            private String body = "";

            private ApiRequestBuilder() {

            }

            public ApiRequestBuilder method(String method) {
                Objects.requireNonNull(method);
                this.method = method;
                return this;
            }

            public ApiRequestBuilder url(String url) {
                Objects.requireNonNull(url);
                this.url = url;
                return this;
            }

            public ApiRequestBuilder type(String type) {
                Objects.requireNonNull(type);
                this.type = type;
                return this;
            }

            public ApiRequestBuilder body(String body) {
                Objects.requireNonNull(body);
                this.body = body;
                return this;
            }

            public ApiRequestBuilder bodyFromJsonObject(Function<JsonObjectBuilder<?, ?>, String> jsonBuilder) {
                Objects.requireNonNull(jsonBuilder);
                this.body = jsonBuilder.apply(JsonBuilderFactory.buildObject());
                return this;
            }

            public ApiRequestBuilder bodyFromJsonArray(Function<JsonArrayBuilder, String> jsonBuilder) {
                Objects.requireNonNull(jsonBuilder);
                this.body = jsonBuilder.apply(JsonBuilderFactory.buildArray());
                return this;
            }

            public ApiRequestBuilder bodyFromFile(File body) {
                try {
                    Objects.requireNonNull(body);
                    this.body = FileUtils.readFileToString(body);
                    return this;
                } catch (IOException e) {
                    throw new IllegalStateException(format("Cannot open file: %s", body), e);
                }
            }

            public ApiRequestBuilder header(String name, String value) {
                Objects.requireNonNull(name);
                Objects.requireNonNull(value);
                headers.add(new ApiHeader(name, value));
                return this;
            }

            public ApiRequestBuilder queryParam(String name, String value) {
                Objects.requireNonNull(name);
                Objects.requireNonNull(value);
                queryParams.add(new ApiQueryParam(name, value));
                return this;
            }

            public ApiCallBuilder done() {
                request = new ApiRequest(method, url, headers, type, body, queryParams);
                return ApiCallBuilder.this;
            }
        }

        public class ApiResponseBuilder {
            private List<ApiHeader> headers = new LinkedList<>();
            private int statusCode = 0;
            private String type = "";
            private String body = "";

            private ApiResponseBuilder() {
            }

            public ApiResponseBuilder statusCode(int statusCode) {
                Objects.requireNonNull(statusCode);
                this.statusCode = statusCode;
                return this;
            }

            public ApiResponseBuilder type(String type) {
                Objects.requireNonNull(type);
                this.type = type;
                return this;
            }

            public ApiResponseBuilder body(String body) {
                Objects.requireNonNull(body);
                this.body = body;
                return this;
            }

            public ApiResponseBuilder header(String name, String value) {
                Objects.requireNonNull(name);
                Objects.requireNonNull(value);
                headers.add(new ApiHeader(name, value));
                return this;
            }

            public ApiCallBuilder done() {
                response = new ApiResponse(headers, 0, type, body);
                return ApiCallBuilder.this;
            }
        }
    }
}
