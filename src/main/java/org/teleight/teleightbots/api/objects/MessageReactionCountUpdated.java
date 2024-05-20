package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record MessageReactionCountUpdated(
        @JsonProperty(value = "chat", required = true)
        @NotNull
        Chat chat,

        @JsonProperty(value = "message_id", required = true)
        @NotNull
        Integer messageId,

        @JsonProperty(value = "date", required = true)
        @NotNull
        Integer date,

        @JsonProperty(value = "reactions", required = true)
        @NotNull
        ReactionCount[] reactions
) implements ApiResult {
}
