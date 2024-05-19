package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record BusinessConnection(
        @JsonProperty(value = "id", required = true)
        @NotNull
        String id,

        @JsonProperty(value = "user", required = true)
        @NotNull
        User user,

        @JsonProperty(value = "user_chat_id", required = true)
        @NotNull
        Long userChatId,

        @JsonProperty(value = "date", required = true)
        int date,

        @JsonProperty(value = "can_reply", required = true)
        boolean canReply,

        @JsonProperty(value = "is_enabled", required = true)
        boolean isEnabled
) implements ApiResult {
}
