package org.teleight.teleightbots.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;
import org.teleight.teleightbots.utils.MultiPartBodyPublisher;

public interface MultiPartApiMethodBoolean extends ApiMethod<Boolean> {

    void buildRequest(MultiPartBodyPublisher bodyCreator) throws JsonProcessingException;

    @Override
    default @NotNull Boolean deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Boolean.class);
    }
}
