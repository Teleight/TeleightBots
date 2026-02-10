package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record GiftInfo(
        @JsonProperty(value = "gift", required = true)
        @NotNull
        Gift gift,

        @JsonProperty(value = "owned_gift_id")
        @Nullable
        String ownedGiftId,

        @JsonProperty(value = "convert_star_count")
        @Nullable
        Integer convertStarCount,

        @JsonProperty(value = "prepaid_upgrade_star_count")
        @Nullable
        Integer prepaidUpgradeStarCount,

        @JsonProperty(value = "is_upgrade_separate")
        boolean isUpgradeSeparate,

        @JsonProperty(value = "can_be_upgraded")
        boolean canBeUpgraded,

        @JsonProperty(value = "text")
        @Nullable
        String text,

        @JsonProperty(value = "entities")
        @Nullable
        MessageEntity[] entities,

        @JsonProperty(value = "is_private")
        boolean isPrivate,

        @JsonProperty(value = "unique_gift_number")
        int uniqueGiftNumber
) implements ApiResult {
}
