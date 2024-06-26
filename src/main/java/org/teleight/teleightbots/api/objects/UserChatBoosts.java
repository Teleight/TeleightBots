package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record UserChatBoosts(
        @JsonProperty(value = "boosts", required = true)
        @NotNull
        ChatBoost[] boosts
) implements ApiResult {
}
