package org.teleight.teleightbots.api.objects.chat.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.User;

public record ChatMemberAdministrator(
        @JsonProperty("status")
        String status,

        @JsonProperty("user")
        User user,

        @JsonProperty("can_be_edited")
        boolean canBeEdited,

        @JsonProperty("is_anonymous")
        boolean isAnonymous,

        @JsonProperty("can_manage_chat")
        boolean canManageChat,

        @JsonProperty("can_delete_messages")
        boolean canDeleteMessages,

        @JsonProperty("can_manage_voice_chats")
        boolean canManageVoiceChats,

        @JsonProperty("can_restrict_members")
        boolean canRestrictMembers,

        @JsonProperty("can_promote_members")
        boolean canPromoteMembers,

        @JsonProperty("can_change_info")
        boolean canChangeInfo,

        @JsonProperty("can_invite_users")
        boolean canInviteUsers,

        @JsonProperty("can_post_messages")
        @Nullable
        Boolean canPostMessages,

        @JsonProperty("can_edit_messages")
        @Nullable
        Boolean canEditMessages,

        @JsonProperty("can_pin_messages")
        @Nullable
        Boolean canPinMessages,

        @JsonProperty("can_post_stories")
        @Nullable
        Boolean canPostStories,

        @JsonProperty("can_edit_stories")
        @Nullable
        Boolean canEditStories,

        @JsonProperty("can_delete_stories")
        @Nullable
        Boolean canDeleteStories,

        @JsonProperty("can_manage_topics")
        @Nullable
        Boolean canManageTopics,

        @JsonProperty("custom_title")
        @Nullable
        String customTitle
) implements ChatMember, ApiResult {

        @Override
        public ChatMemberType type() {
                return ChatMemberType.ADMINISTRATOR;
        }

}
