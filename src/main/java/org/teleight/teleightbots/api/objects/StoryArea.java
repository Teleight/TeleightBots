package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record StoryArea(
        @JsonProperty(value = "position", required = true)
        @NotNull
        StoryAreaPosition position,

        @JsonProperty(value = "type", required = true)
        @NotNull
        StoryAreaType type
) implements ApiResult {
}
