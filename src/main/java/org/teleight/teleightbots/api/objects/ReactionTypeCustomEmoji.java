package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record ReactionTypeCustomEmoji(
        @JsonProperty(value = "type", required = true, defaultValue = "custom_emoji")
        @NotNull
        String type,

        @JsonProperty(value = "custom_emoji", required = true)
        @NotNull
        String customEmoji
) implements ReactionType, ApiResult {
}
