package org.teleight.teleightbots.api;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

/**
 * Interface representing a multipart API method that returns a Boolean.
 */
public interface MultiPartApiMethodBoolean extends MultiPartApiMethod<Boolean> {

    @Override
    default @NotNull Boolean deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Boolean.class);
    }

}
