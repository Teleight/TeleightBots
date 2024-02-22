package org.teleight.teleightbots.api.objects.origin;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.chat.Chat;

import java.util.Date;

public record MessageOriginChat(
        @JsonProperty(value = "type", required = true, defaultValue = "chat")
        @NotNull
        String type,

        @JsonProperty(value = "date", required = true)
        @NotNull
        Date date,

        @JsonProperty(value = "sender_chat", required = true)
        @NotNull
        Chat senderChat,

        @JsonProperty(value = "author_signature")
        @NotNull
        String senderSignature
) implements MessageOrigin, ApiResult {
}
