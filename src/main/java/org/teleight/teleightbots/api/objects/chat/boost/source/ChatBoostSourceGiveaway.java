package org.teleight.teleightbots.api.objects.chat.boost.source;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.objects.User;

public record ChatBoostSourceGiveaway(
        @JsonProperty(value = "source", required = true, defaultValue = "giveaway")
        @NotNull
        String source,

        @JsonProperty(value = "giveaway_message_id", required = true)
        @NotNull
        Integer giveawayMessageId,

        @JsonProperty(value = "user")
        @Nullable
        User user,

        @JsonProperty(value = "is_unclaimed")
        @Nullable
        Boolean isUnclaimed
) implements ChatBoostSource {
}
