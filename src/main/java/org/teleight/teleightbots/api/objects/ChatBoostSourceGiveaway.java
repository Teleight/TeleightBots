package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

public record ChatBoostSourceGiveaway(
        @JsonProperty(value = "giveaway_message_id", required = true)
        int giveawayMessageId,

        @JsonProperty(value = "user")
        @Nullable
        User user,

        @JsonProperty(value = "is_unclaimed")
        boolean isUnclaimed
) implements ChatBoostSource {

    @Override
    public ChatBoostSourceType source() {
        return ChatBoostSourceType.GIVEAWAY;
    }

}
