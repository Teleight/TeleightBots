/*
 * This file is part of TelegramBots.
 *
 * TelegramBots is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TelegramBots is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TelegramBots.  If not, see <http://www.gnu.org/licenses/>.
 */

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
        if (errorDescription == null) {
            return super.getMessage();
        } else if (errorCode != 0) {
            return super.getMessage() + ": " + errorDescription;
        } else {
            return super.getMessage() + ": [" + errorCode + "] " + errorDescription;
        }
    }

    @Override
    public String toString() {
        return getMessage();
    }

}
