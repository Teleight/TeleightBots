package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record UserRating(
        @JsonProperty(value = "level", required = true)
        int level,

        @JsonProperty(value = "rating", required = true)
        int rating,

        @JsonProperty(value = "current_level_rating", required = true)
        int currentLevelRating,

        @JsonProperty(value = "next_level_rating")
        int nextLevelRating
) implements ApiResult {
}
