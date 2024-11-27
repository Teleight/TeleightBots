package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record Gifts(
        @JsonProperty(value = "gifts", required = true)
        @NotNull
        Gift[] gifts
) implements ApiResult {
}
