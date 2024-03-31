package org.teleight.teleightbots.api.objects.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.User;

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
        @NotNull
        Integer date,

        @JsonProperty(value = "can_reply", required = true)
        @NotNull
        Boolean canReply,

        @JsonProperty(value = "is_enabled", required = true)
        @NotNull
        Boolean isEnabled
) implements ApiResult {
}
