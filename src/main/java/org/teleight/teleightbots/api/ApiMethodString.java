package org.teleight.teleightbots.api;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

/**
 * Represents a method that returns a string result.
 */
public interface ApiMethodString extends ApiMethod<String> {

    @Override
    default @NotNull String deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, String.class);
    }

}
