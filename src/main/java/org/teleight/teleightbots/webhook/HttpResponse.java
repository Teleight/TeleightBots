package org.teleight.teleightbots.webhook;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an HTTP response used to respond to a webhook request.
 */
public record HttpResponse(@NotNull StatusCode statusCode, @NotNull String body) {

    /**
     * Creates an HTTP response with status code 200 (OK) and the specified response body.
     *
     * @param responseBody The body of the response. Cannot be null.
     * @return An HttpResponse with status code 200 (OK).
     */
    public static HttpResponse ok(@NotNull String responseBody) {
        return new HttpResponse(StatusCode.OK, responseBody);
    }

    /**
     * Creates an HTTP response with status code 500 (Internal Server Error) and the specified response body.
     *
     * @param responseBody The body of the response. Cannot be null.
     * @return An HttpResponse with status code 500 (Internal Server Error).
     */
    public static HttpResponse error(@NotNull String responseBody) {
        return new HttpResponse(StatusCode.INTERNAL_SERVER_ERROR, responseBody);
    }

    /**
     * Creates an HTTP response with status code 204 (No Content) and an empty body.
     *
     * @return An HttpResponse with status code 204 (No Content).
     */
    public static HttpResponse noContent() {
        return new HttpResponse(StatusCode.NO_CONTENT, "");
    }

    /**
     * Creates an HTTP response with the specified status code and response body.
     *
     * @param statusCode   The status code of the response. Cannot be null.
     * @param responseBody The body of the response. Cannot be null.
     * @return An HttpResponse with the specified status code and response body.
     */
    public static HttpResponse response(@NotNull StatusCode statusCode, @NotNull String responseBody) {
        return new HttpResponse(statusCode, responseBody);
    }

    public enum StatusCode {
        OK(200),
        CREATED(201),
        ACCEPTED(202),
        NO_CONTENT(204),
        BAD_REQUEST(400),
        UNAUTHORIZED(401),
        FORBIDDEN(403),
        NOT_FOUND(404),
        INTERNAL_SERVER_ERROR(500);

        private final int code;

        StatusCode(int code) {
            this.code = code;
        }

        /**
         * Gets the HTTP status code.
         *
         * @return the HTTP status code
         */
        public int getCode() {
            return code;
        }
    }

}
