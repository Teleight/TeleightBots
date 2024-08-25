package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record PaidMediaInfo(
        @JsonProperty(value = "star_count", required = true)
        int startCount,

        @JsonProperty(value = "paid_media", required = true)
        @NotNull
        PaidMedia[] paidMedia
) implements ApiResult {
}
