package org.teleight.teleightbots.api;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

/**
 * Interface representing a multipart API method that returns a Message.
 */
public interface MultiPartApiMethodMessage extends MultiPartApiMethod<Message> {

    @Override
    default @NotNull Message deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Message.class);
    }

}
