package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record ManagedBotCreated(
        @JsonProperty(value = "bot", required = true)
        @NotNull
        User bot
) implements ApiResult {
}
