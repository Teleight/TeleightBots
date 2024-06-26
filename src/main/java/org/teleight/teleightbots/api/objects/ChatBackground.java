package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record ChatBackground(
        @JsonProperty(value = "type", required = true)
        @NotNull
        BackgroundType type
) implements ApiResult {
}
