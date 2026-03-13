package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

        @JsonProperty(value = "is_premium")
        boolean isPremium,

        @JsonProperty(value = "has_colors")
        boolean hasColors,

        @JsonProperty(value = "total_count")
        int totalCount,

        @JsonProperty(value = "remaining_count")
        int remainingCount,

        @JsonProperty(value = "personal_total_count")
        int personalTotalCount,

        @JsonProperty(value = "personal_remaining_count")
        int personalRemainingCount,

        @JsonProperty(value = "background")
        @Nullable
        GiftBackground background,

        @JsonProperty(value = "unique_gift_variant_count")
        int uniqueGiftVariantCount,

        @JsonProperty(value = "publisher_chat")
        @Nullable
        Chat publisherChat
) implements ApiResult {
}
