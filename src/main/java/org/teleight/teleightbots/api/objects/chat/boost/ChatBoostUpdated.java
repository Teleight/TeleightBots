package org.teleight.teleightbots.api.objects.chat.boost;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.chat.Chat;

public record ChatBoostUpdated(
        @JsonProperty(value = "chat", required = true)
        @NotNull
        Chat chat,

        @JsonProperty(value = "boost", required = true)
        @NotNull
        ChatBoost boost
) implements ApiResult {
}
