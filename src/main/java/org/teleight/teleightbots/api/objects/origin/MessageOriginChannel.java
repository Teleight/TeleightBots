package org.teleight.teleightbots.api.objects.origin;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.chat.Chat;

import java.util.Date;

public record MessageOriginChannel(
        @JsonProperty(value = "type", required = true, defaultValue = "channel")
        @NotNull
        String type,

        @JsonProperty(value = "date", required = true)
        @NotNull
        Date date,

        @JsonProperty(value = "chat", required = true)
        @NotNull
        Chat chat,

        @JsonProperty(value = "message_id", required = true)
        @NotNull
        Integer messageId,

        @JsonProperty(value = "author_signature")
        @NotNull
        String senderSignature
) implements MessageOrigin, ApiResult {
}
