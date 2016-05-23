package org.kpull.bastion.support.embedded;

import spark.Response;

/**
 * Error codes and descriptions for the Sushi Service test API.
 */
public enum SushiError {

    INVALID_ENTITY(400, "The provided entity is not a valid entity for this request."),
    NOT_AUTHENTICATED(401, "You need to be authenticated!"),
    NOT_FOUND(404, "The requested order could not be found."),
    INTERNAL_SERVER_ERROR(500, "Something has gone terribly wrong. Oops!");

    private int statusCode;
    private String reason;

    SushiError(final int statusCode, final String reason) {
        this.statusCode = statusCode;
        this.reason = reason;
    }

    /**
     * Returns a response for this error code and sets the HTTP status code for the given Spark response.
     * @param response the Spark response to set the HTTP status for
     * @return An error response object with the default error reason.
     */
    public SushiOrderErrorResponse toResponse(final Response response) {
        response.status(statusCode);
        return new SushiOrderErrorResponse(name(), reason);
    }

    /**
     * Returns a response for this error code with a custom reason and sets the HTTP status code for the given Spark response.
     * @param response the Spark response to set the HTTP status for
     * @param reason the reason for the error
     * @return An error response object with a custom error reason.
     */
    public SushiOrderErrorResponse toResponse(final Response response, final String reason) {
        response.status(statusCode);
        return new SushiOrderErrorResponse(name(), reason);
    }

    /**
     * A response model for API errors.
     */
    public class SushiOrderErrorResponse {

        private String code;
        private String reason;

        public SushiOrderErrorResponse(final String code, final String reason) {
            this.code = code;
            this.reason = reason;
        }
    }
}