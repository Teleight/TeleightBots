package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record GameHighScore(
        @JsonProperty(value = "position", required = true)
        int position,

        @JsonProperty(value = "user", required = true)
        @NotNull
        User user,

        @JsonProperty(value = "score", required = true)
        int score
) implements ApiResult {
}
