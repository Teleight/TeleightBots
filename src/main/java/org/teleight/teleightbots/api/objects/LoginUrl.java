package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record LoginUrl(
        @JsonProperty(value = "url", required = true)
        @NotNull
        String url,

        @JsonProperty("forward_text")
        @Nullable
        String forwardText,

        @JsonProperty("bot_username")
        @Nullable
        String botUsername,

        @JsonProperty("request_write_access")
        boolean requestWriteAccess
) implements ApiResult {
}
