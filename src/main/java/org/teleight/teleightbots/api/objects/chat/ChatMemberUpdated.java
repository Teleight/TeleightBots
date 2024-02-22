package org.teleight.teleightbots.api.objects.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.api.objects.chat.member.ChatMember;

import java.util.Date;

public record ChatMemberUpdated(
        @JsonProperty(value = "chat", required = true)
        @NotNull
        Chat chat,

        @JsonProperty(value = "from", required = true)
        @NotNull
        User from,

        @JsonProperty(value = "date", required = true)
        @NotNull
        Date date,

        @JsonProperty(value = "old_chat_member", required = true)
        @NotNull
        ChatMember oldChatMember,

        @JsonProperty(value = "new_chat_member", required = true)
        @NotNull
        ChatMember newChatMember,

        @JsonProperty("invite_link")
        @Nullable
        ChatInviteLink inviteLink,

        @JsonProperty("via_chat_folder_invite_link")
        @Nullable
        Boolean viaChatFolderInviteLink
) implements ApiResult {
}
