package org.kpull.apitestsuites.core;

import java.util.*;

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
        private String postCallScript = "";

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

        public ApiCallBuilder postCallScript(String postCallScript) {
            Objects.requireNonNull(postCallScript);
            this.postCallScript = postCallScript;
            return this;
        }

        public ApiCallBuilder responseModel(Class<?> responseModel) {
            Objects.requireNonNull(responseModel);
            this.responseModel = responseModel;
            return this;
        }

        public ApiRequestBuilder request() {
            return new ApiRequestBuilder();
        }

        public ApiResponseBuilder response() {
            return new ApiResponseBuilder();
        }

        public ApiSuiteBuilder done() {
            apiCalls.add(new ApiCall(name, description, request, response, responseModel, postCallScript));
            return ApiSuiteBuilder.this;
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
