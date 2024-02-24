package org.teleight.teleightbots.api.objects.chat.boost.source;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.User;

public record ChatBoostSourcePremium(
        @JsonProperty(value = "source", required = true, defaultValue = "premium")
        @NotNull
        String source,

        @JsonProperty(value = "user", required = true)
        @NotNull
        User user
) implements ChatBoostSource {
}
