package org.teleight.teleightbots.api.objects.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.api.objects.chat.member.ChatMember;

public record ChatMemberUpdated(
        @JsonProperty(value = "chat", required = true)
        Chat chat,

        @JsonProperty(value = "from", required = true)
        User from,

        @JsonProperty(value = "date", required = true)
        int date,

        @JsonProperty(value = "old_chat_member", required = true)
        ChatMember oldChatMember,

        @JsonProperty(value = "new_chat_member", required = true)
        ChatMember newChatMember,

        @JsonProperty("invite_link")
        @Nullable
        ChatInviteLink inviteLink,

        @JsonProperty("via_chat_folder_invite_link")
        @Nullable
        Boolean viaChatFolderInviteLink
) implements ApiResult {
}
