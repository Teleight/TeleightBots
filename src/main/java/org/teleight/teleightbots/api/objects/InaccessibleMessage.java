package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.chat.Chat;

public record InaccessibleMessage(
        @JsonProperty(value = "chat", required = true)
        @NotNull
        Chat chat,

        @JsonProperty(value = "message_id", required = true)
        @NotNull
        Integer messageId,

        @JsonProperty(value = "date", required = true, defaultValue = "0")
        @NotNull
        Integer date
) implements MaybeInaccessibleMessage, ApiResult {
}
