package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

import java.util.Date;

public record UniqueGiftInfo(
        @JsonProperty(value = "gift", required = true)
        @NotNull
        UniqueGift gift,

        @JsonProperty(value = "origin", required = true)
        @NotNull
        UniqueGiftOrigin origin,

        @JsonProperty(value = "last_resale_currency")
        TelegramCurrency lastResaleCurrency,

        @JsonProperty(value = "last_resale_amount")
        int lastResaleAmount,

        @JsonProperty(value = "owned_gift_id")
        @Nullable
        String ownedGiftId,

        @JsonProperty(value = "transfer_star_count")
        @Nullable
        Integer transferStarCount,

        @JsonProperty(value = "next_transfer_date")
        @Nullable
        Date nextTransferDate
) implements ApiResult {
}
