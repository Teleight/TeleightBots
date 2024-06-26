package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

import java.util.Date;

public record GiveawayWinners(
        @JsonProperty(value = "chat", required = true)
        @NotNull
        Chat chat,

        @JsonProperty(value = "giveaway_message_id", required = true)
        int giveawayMessageId,

        @JsonProperty(value = "winners_selection_date", required = true)
        @NotNull
        Date winnersSelectionDate,

        @JsonProperty(value = "winner_count", required = true)
        int winnerCount,

        @JsonProperty(value = "winners", required = true)
        @NotNull
        User[] winners,

        @JsonProperty(value = "additional_chat_count")
        int additionalChatCount,

        @JsonProperty(value = "premium_subscription_month_count")
        int premiumSubscriptionMonthCount,

        @JsonProperty(value = "unclaimed_prize_count")
        int unclaimedPrizeCount,

        @JsonProperty(value = "only_new_members")
        boolean onlyNewMembers,

        @JsonProperty(value = "was_refunded")
        boolean wasRefunded,

        @JsonProperty(value = "prize_description")
        @Nullable
        String prizeDescription
) implements ApiResult {
}
