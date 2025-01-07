package org.teleight.teleightbots.webhook;

public class HttpResponseHandler {

    private StatusCode statusCode;
    private String body;

    private HttpResponseHandler(String body) {
        this.body = body;
        this.statusCode = StatusCode.OK;  // Default to HTTP 200 OK
    }

    /**
     * Static factory method to create an HttpResponseHandler instance from a body.
     *
     * @param body the body of the HTTP response
     * @return a new instance of HttpResponseHandler
     */
    public static HttpResponseHandler fromBody(String body) {
        return new HttpResponseHandler(body);
    }

    /**
     * Gets the body of the HTTP response.
     *
     * @return the body of the HTTP response
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the body of the HTTP response.
     *
     * @param body the new body of the HTTP response
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Gets the status code of the HTTP response.
     *
     * @return the status code of the HTTP response
     */
    public StatusCode getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the status code of the HTTP response.
     *
     * @param statusCode the new status code of the HTTP response
     */
    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
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
