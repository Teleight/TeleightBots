package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record Story(
        @JsonProperty(value = "chat", required = true)
        @NotNull
        Chat chat,

        @JsonProperty(value = "id", required = true)
        int id
) implements ApiResult {
}
