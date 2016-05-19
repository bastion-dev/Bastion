package org.kpull.bastion.support.embedded;

import spark.Response;

public enum SushiError {

    NOT_AUTHENTICATED(401, "You need to be authenticated!"),
    NOT_FOUND(404, "The requested order could not be found."),
    INTERNAL_SERVER_ERROR(500, "Something has gone terribly wrong. Oops!");

    private int statusCode;
    private String reason;

    SushiError(final int statusCode, final String reason) {
        this.statusCode = statusCode;
        this.reason = reason;
    }

    public SushiOrderErrorResponse toResponse(final Response response) {
        response.status(statusCode);
        return new SushiOrderErrorResponse(name(), reason);
    }

    public SushiOrderErrorResponse toResponse(final Response response, final String reason) {
        response.status(statusCode);
        return new SushiOrderErrorResponse(name(), reason);
    }

    public class SushiOrderErrorResponse {

        private String code;
        private String reason;

        public SushiOrderErrorResponse(final String code, final String reason) {
            this.code = code;
            this.reason = reason;
        }
    }
}