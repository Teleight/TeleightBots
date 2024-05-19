package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record MessageReactionCountUpdated(
        @JsonProperty(value = "chat", required = true)
        @NotNull
        Chat chat,

        @JsonProperty(value = "message_id", required = true)
        int messageId,

        @JsonProperty(value = "date", required = true)
        int date,

        @JsonProperty(value = "reactions", required = true)
        @NotNull
        ReactionCount[] reactions
) implements ApiResult {
}
