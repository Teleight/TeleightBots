package org.teleight.teleightbots.webhook;

public class HttpHandler {

    private StatusCode statusCode;
    private String body;

    private HttpHandler(String body) {
        this.body = body;
        this.statusCode = StatusCode.OK;  // Default to HTTP 200 OK
    }

    public static HttpHandler create(String body) {
        return new HttpHandler(body);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

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

        public int getCode() {
            return code;
        }
    }

}
