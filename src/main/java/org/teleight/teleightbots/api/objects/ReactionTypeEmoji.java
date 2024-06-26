package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record ReactionTypeEmoji(
        @JsonProperty(value = "type", required = true, defaultValue = "emoji")
        @NotNull
        String type,

        @JsonProperty(value = "emoji", required = true)
        @NotNull
        String emoji
) implements ReactionType, ApiResult {
}
