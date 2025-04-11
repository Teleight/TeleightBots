package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public record OwnedGiftRegular(
        @JsonProperty(value = "gift", required = true)
        @NotNull
        Gift gift,

        @JsonProperty(value = "owned_gift_id")
        @Nullable
        String ownedGiftId,

        @JsonProperty(value = "sender_user")
        @Nullable
        User senderUser,

        @JsonProperty(value = "send_date", required = true)
        @NotNull
        Date sendDate,

        @JsonProperty(value = "text")
        @Nullable
        String text,

        @JsonProperty(value = "entities")
        @Nullable
        MessageEntity[] entities,

        @JsonProperty(value = "is_private")
        boolean isPrivate,

        @JsonProperty(value = "is_saved")
        boolean isSaved,

        @JsonProperty(value = "can_be_upgraded")
        boolean canBeUpgraded,

        @JsonProperty(value = "was_refunded")
        boolean wasRefunded,

        @JsonProperty(value = "convert_star_count")
        @Nullable
        Integer convertStarCount,

        @JsonProperty(value = "prepaid_upgrade_star_count")
        @Nullable
        Integer prepaidUpgradeStarCount
) implements OwnedGift {

    @Override
    public String type() {
        return "regular";
    }

}
