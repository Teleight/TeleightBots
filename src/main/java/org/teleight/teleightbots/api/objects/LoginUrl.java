package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record LoginUrl(
        @JsonProperty(value = "url", required = true)
        @NotNull
        String url,

        @JsonProperty(value = "forward_text")
        @Nullable
        String forwardText,

        @JsonProperty(value = "bot_username")
        @Nullable
        String botUsername,

        @JsonProperty(value = "request_write_access")
        boolean requestWriteAccess
) implements ApiResult {
}
