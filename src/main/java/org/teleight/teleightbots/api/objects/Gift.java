package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record Gift(
        @JsonProperty(value = "id", required = true)
        @NotNull
        String id,

        @JsonProperty(value = "sticker", required = true)
        @NotNull
        Sticker sticker,

        @JsonProperty(value = "star_count", required = true)
        int starCount,

        @JsonProperty(value = "upgrade_star_count")
        int upgradeStarCount,

        @JsonProperty(value = "total_count")
        int totalCount,

        @JsonProperty(value = "remaining_count")
        int remainingCount
) implements ApiResult {
}
