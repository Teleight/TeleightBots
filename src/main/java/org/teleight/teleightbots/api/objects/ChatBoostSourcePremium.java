package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public record ChatBoostSourcePremium(
        @JsonProperty(value = "user", required = true)
        @NotNull
        User user
) implements ChatBoostSource {

    @Override
    public String source() {
        return "premium";
    }

}
