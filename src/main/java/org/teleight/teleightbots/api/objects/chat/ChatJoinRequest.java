package org.teleight.teleightbots.api.objects.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.User;

public record ChatJoinRequest(
        @JsonProperty(value = "chat", required = true)
        Chat chat,

        @JsonProperty(value = "from", required = true)
        User from,

        @JsonProperty(value = "user_chat_id", required = true)
        long userChatId,

        @JsonProperty(value = "date", required = true)
        int date,

        @JsonProperty("bio")
        @Nullable
        String bio,

        @JsonProperty("invite_link")
        @Nullable
        ChatInviteLink inviteLink
) implements ApiResult {
}
