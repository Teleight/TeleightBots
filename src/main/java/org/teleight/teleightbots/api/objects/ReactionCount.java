package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record ReactionCount(
        @JsonProperty(value = "type", required = true)
        @NotNull
        ReactionType type,

        @JsonProperty(value = "total_count", required = true)
        @NotNull
        String emoji
) implements ApiResult {
}
