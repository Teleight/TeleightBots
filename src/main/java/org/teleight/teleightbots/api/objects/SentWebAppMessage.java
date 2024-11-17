package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record SentWebAppMessage(
        @JsonProperty(value = "inline_message_id")
        @Nullable
        String inlineMessageId
) implements ApiResult {
}
