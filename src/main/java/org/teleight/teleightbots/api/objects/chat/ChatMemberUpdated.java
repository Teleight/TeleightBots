package org.teleight.teleightbots.api.objects.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.api.objects.chat.member.ChatMember;

public record ChatMemberUpdated(
        @JsonProperty("chat")
        Chat chat,

        @JsonProperty("from")
        User from,

        @JsonProperty("date")
        int date,

        @JsonProperty("old_chat_member")
        ChatMember oldChatMember,

        @JsonProperty("new_chat_member")
        ChatMember newChatMember,

        @JsonProperty("invite_link")
        @Nullable
        ChatInviteLink inviteLink,

        @JsonProperty("via_chat_folder_invite_link")
        @Nullable
        Boolean viaChatFolderInviteLink
) implements ApiResult {
}
