package org.teleight.teleightbots.api.objects.giveaway;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.Message;

public record GiveawayCompleted(
        @JsonProperty(value = "winner_count", required = true)
        @NotNull
        Integer winnerCount,

        @JsonProperty(value = "unclaimed_prize_count")
        @Nullable
        Integer unclaimedPrizeCount,

        @JsonProperty(value = "giveaway_message")
        @Nullable
        Message giveawayMessage
) implements ApiResult {
}
