package org.teleight.teleightbots.api.objects.giveaway;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.chat.Chat;

import java.util.Date;

public record Giveaway(
        @JsonProperty(value = "chats", required = true)
        @NotNull
        Chat[] chats,

        @JsonProperty(value = "winners_selection_date", required = true)
        @NotNull
        Date winnersSelectionDate,

        @JsonProperty(value = "winner_count", required = true)
        @NotNull
        Integer winnerCount,

        @JsonProperty(value = "only_new_members")
        @Nullable
        Boolean onlyNewMembers,

        @JsonProperty(value = "has_public_winners")
        @Nullable
        Boolean hasPublicWinners,

        @JsonProperty(value = "prize_description")
        @Nullable
        String prizeDescription,

        @JsonProperty(value = "country_codes")
        @Nullable
        String[] countryCodes,

        @JsonProperty(value = "premium_subscription_month_count")
        @Nullable
        Integer premiumSubscriptionMonthCount
) implements ApiResult {
}
