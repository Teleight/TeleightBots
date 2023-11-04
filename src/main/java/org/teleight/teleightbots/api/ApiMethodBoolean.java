package org.teleight.teleightbots.api;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public interface ApiMethodBoolean extends ApiMethod<Boolean> {

    default @NotNull Boolean deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Boolean.class);
    }

}
