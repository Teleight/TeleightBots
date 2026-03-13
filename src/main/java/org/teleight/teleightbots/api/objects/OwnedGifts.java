package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record OwnedGifts(
        @JsonProperty(value = "total_count", required = true)
        int totalCount,

        @JsonProperty(value = "gifts", required = true)
        @NotNull
        OwnedGift[] gifts,

        @JsonProperty(value = "next_offset")
        @Nullable
        String nextOffset
) implements ApiResult {
}
