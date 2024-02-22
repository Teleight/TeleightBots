package org.teleight.teleightbots.api;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Message;

public interface MultiPartApiMethodMessage extends MultiPartApiMethod<Message> {

    default @NotNull Message deserializeResponse(@NotNull String answer) {
        return deserializeResponse(answer, Message.class);
    }

}
