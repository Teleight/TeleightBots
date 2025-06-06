package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record UniqueGiftInfo(
        @JsonProperty(value = "gift", required = true)
        @NotNull
        UniqueGift gift,

        @JsonProperty(value = "origin", required = true)
        @NotNull
        UniqueGiftOrigin origin,

        @JsonProperty(value = "owned_gift_id")
        @Nullable
        String ownedGiftId,

        @JsonProperty(value = "transfer_star_count")
        @Nullable
        Integer transferStarCount
) implements ApiResult {
}
