package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public record MessageOriginChat(
        @JsonProperty(value = "date", required = true)
        @NotNull
        Date date,

        @JsonProperty(value = "sender_chat", required = true)
        @NotNull
        Chat senderChat,

        @JsonProperty(value = "author_signature")
        @NotNull
        String senderSignature
) implements MessageOrigin {

    @Override
    public MessageOriginType type() {
        return MessageOriginType.CHAT;
    }

}
