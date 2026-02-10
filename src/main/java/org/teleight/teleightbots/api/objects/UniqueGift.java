package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record UniqueGift(
        @JsonProperty(value = "gift_id", required = true)
        @NotNull
        String giftId,

        @JsonProperty(value = "base_name", required = true)
        @NotNull
        String baseName,

        @JsonProperty(value = "name", required = true)
        @NotNull
        String name,

        @JsonProperty(value = "number", required = true)
        int number,

        @JsonProperty(value = "model", required = true)
        @NotNull
        UniqueGiftModel model,

        @JsonProperty(value = "symbol", required = true)
        @NotNull
        UniqueGiftSymbol symbol,

        @JsonProperty(value = "backdrop", required = true)
        @NotNull
        UniqueGiftBackdrop backdrop,

        @JsonProperty(value = "is_premium")
        boolean isPremium,

        @JsonProperty(value = "is_from_blockchain")
        boolean isFromBlockchain,

        @JsonProperty(value = "colors")
        @Nullable
        UniqueGiftColors colors,

        @JsonProperty(value = "publisher_chat")
        @Nullable
        Chat publisherChat
) implements ApiResult {
}
