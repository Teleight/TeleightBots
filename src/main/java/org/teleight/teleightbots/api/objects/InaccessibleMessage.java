package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public record InaccessibleMessage(
        @JsonProperty(value = "chat", required = true)
        @NotNull
        Chat chat,

        @JsonProperty(value = "message_id", required = true)
        int messageId,

        @JsonProperty(value = "date", required = true, defaultValue = "0")
        int date
) implements MaybeInaccessibleMessage {
}
