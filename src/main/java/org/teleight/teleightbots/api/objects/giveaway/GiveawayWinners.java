package org.teleight.teleightbots.api.objects.giveaway;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.api.objects.chat.Chat;

import java.util.Date;

public record GiveawayWinners(
        @JsonProperty(value = "chat", required = true)
        @NotNull
        Chat chat,

        @JsonProperty(value = "giveaway_message_id", required = true)
        @NotNull
        Integer giveawayMessageId,

        @JsonProperty(value = "winners_selection_date", required = true)
        @NotNull
        Date winnersSelectionDate,

        @JsonProperty(value = "winner_count", required = true)
        @NotNull
        Integer winnerCount,

        @JsonProperty(value = "winners", required = true)
        @NotNull
        User[] winners,

        @JsonProperty(value = "additional_chat_count")
        @Nullable
        Integer additionalChatCount,

        @JsonProperty(value = "premium_subscription_month_count")
        @Nullable
        Integer premiumSubscriptionMonthCount,

        @JsonProperty(value = "unclaimed_prize_count")
        @Nullable
        Integer unclaimedPrizeCount,

        @JsonProperty(value = "only_new_members")
        @Nullable
        Boolean onlyNewMembers,

        @JsonProperty(value = "was_refunded")
        @Nullable
        Boolean wasRefunded,

        @JsonProperty(value = "prize_description")
        @Nullable
        String prizeDescription
) implements ApiResult {
}
