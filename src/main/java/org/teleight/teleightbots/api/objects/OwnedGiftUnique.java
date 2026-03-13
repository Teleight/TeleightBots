package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public record OwnedGiftUnique(
        @JsonProperty(value = "gift", required = true)
        @NotNull
        UniqueGift gift,

        @JsonProperty(value = "owned_gift_id")
        @Nullable
        String ownedGiftId,

        @JsonProperty(value = "sender_user")
        @Nullable
        User senderUser,

        @JsonProperty(value = "send_date", required = true)
        @NotNull
        Date sendDate,

        @JsonProperty(value = "is_saved")
        boolean isSaved,

        @JsonProperty(value = "can_be_transferred")
        boolean canBeTransferred,

        @JsonProperty(value = "transfer_star_count")
        @Nullable
        Integer transferStarCount,

        @JsonProperty(value = "next_transfer_date")
        @Nullable
        Date nextTransferDate
) implements OwnedGift {

    @Override
    public String type() {
        return "unique";
    }
}
