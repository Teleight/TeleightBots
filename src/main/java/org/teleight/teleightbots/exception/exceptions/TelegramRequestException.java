package org.teleight.teleightbots.exception.exceptions;

import org.teleight.teleightbots.api.objects.ApiResponse;

public class TelegramRequestException extends RuntimeException {

    private int errorCode;
    private String errorDescription;

    public TelegramRequestException(String message, ApiResponse<?> response) {
        super(message);
        errorCode = response.errorCode();
        errorDescription = response.errorDescription();
    }

    public TelegramRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public TelegramRequestException(Throwable e) {
        super(e);
    }

    public TelegramRequestException() {
        super();
    }

    @Override
    public String getMessage() {
        if (errorCode != 0 && errorDescription != null) {
            return super.getMessage() + " [" + errorCode + "] - " + errorDescription;
        }
        if (errorCode != 0) {
            return super.getMessage() + " [" + errorCode + "]";
        }
        if (errorDescription != null) {
            return super.getMessage() + " - " + errorDescription;
        }
        return super.getMessage();
    }

    @Override
    public String toString() {
        return getMessage();
    }

}
