package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.User;

public record ChatBoostSourceGiftCode(
        @JsonProperty(value = "source", required = true, defaultValue = "gift_code")
        @NotNull
        String source,

        @JsonProperty(value = "user", required = true)
        @NotNull
        User user
) implements ChatBoostSource {
}
