package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

public record ChatMemberAdministrator(
        @JsonProperty(value = "user")
        User user,

        @JsonProperty(value = "can_be_edited")
        boolean canBeEdited,

        @JsonProperty(value = "is_anonymous")
        boolean isAnonymous,

        @JsonProperty(value = "can_manage_chat")
        boolean canManageChat,

        @JsonProperty(value = "can_delete_messages")
        boolean canDeleteMessages,

        @JsonProperty(value = "can_manage_voice_chats")
        boolean canManageVoiceChats,

        @JsonProperty(value = "can_restrict_members")
        boolean canRestrictMembers,

        @JsonProperty(value = "can_promote_members")
        boolean canPromoteMembers,

        @JsonProperty(value = "can_change_info")
        boolean canChangeInfo,

        @JsonProperty(value = "can_invite_users")
        boolean canInviteUsers,

        @JsonProperty(value = "can_post_messages")
        boolean canPostMessages,

        @JsonProperty(value = "can_edit_messages")
        boolean canEditMessages,

        @JsonProperty(value = "can_pin_messages")
        boolean canPinMessages,

        @JsonProperty(value = "can_post_stories")
        boolean canPostStories,

        @JsonProperty(value = "can_edit_stories")
        boolean canEditStories,

        @JsonProperty(value = "can_delete_stories")
        boolean canDeleteStories,

        @JsonProperty(value = "can_manage_topics")
        boolean canManageTopics,

        @JsonProperty(value = "can_manage_direct_messages")
        boolean canManageDirectMessages,

        @JsonProperty(value = "custom_title")
        @Nullable
        String customTitle
) implements ChatMember {

    @Override
    public String status() {
        return "administrator";
    }

}
