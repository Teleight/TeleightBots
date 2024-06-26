package org.teleight.teleightbots.exception.exceptions;

import org.teleight.teleightbots.api.objects.ApiResponse;
import org.teleight.teleightbots.api.objects.ResponseParameters;

public class TelegramRequestException extends RuntimeException {

    private String errorDescription;
    private int errorCode;
    private ResponseParameters parameters;

    public TelegramRequestException(String message, ApiResponse<?> response) {
        super(message);
        errorDescription = response.errorDescription();
        errorCode = response.errorCode();
        parameters = response.parameters();
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

    public String getErrorDescription() {
        return errorDescription;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public ResponseParameters getParameters() {
        return parameters;
    }

    @Override
    public String getMessage() {
        if (errorCode != 0) {
            return super.getMessage() + ": " + errorDescription;
        }
        if (errorDescription == null) {
            return super.getMessage();
        }
        return super.getMessage() + ": (" + errorCode + ") " + errorDescription;
    }

    @Override
    public String toString() {
        return getMessage();
    }

}
