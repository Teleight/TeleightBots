package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record GiveawayCompleted(
        @JsonProperty(value = "winner_count", required = true)
        int winnerCount,

        @JsonProperty(value = "unclaimed_prize_count")
        int unclaimedPrizeCount,

        @JsonProperty(value = "giveaway_message")
        @Nullable
        Message giveawayMessage,

        @JsonProperty("is_star_giveaway")
        boolean isStarGiveaway
) implements ApiResult {
}
