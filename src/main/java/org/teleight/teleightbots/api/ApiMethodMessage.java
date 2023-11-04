package org.teleight.teleightbots.api;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public interface ApiMethodMessage extends ApiMethod<Message> {

    default @NotNull Message deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Message.class);
    }

}
