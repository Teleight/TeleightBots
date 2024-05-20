package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.User;

import java.util.Date;

public record ChatJoinRequest(
        @JsonProperty(value = "chat", required = true)
        Chat chat,

        @JsonProperty(value = "from", required = true)
        User from,

        @JsonProperty(value = "user_chat_id", required = true)
        Long userChatId,

        @JsonProperty(value = "date", required = true)
        Date date,

        @JsonProperty("bio")
        @Nullable
        String bio,

        @JsonProperty("invite_link")
        @Nullable
        ChatInviteLink inviteLink
) implements ApiResult {
}
