package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

public record ChatMemberAdministrator(
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
        boolean canPostMessages,

        @JsonProperty("can_edit_messages")
        boolean canEditMessages,

        @JsonProperty("can_pin_messages")
        boolean canPinMessages,

        @JsonProperty("can_post_stories")
        boolean canPostStories,

        @JsonProperty("can_edit_stories")
        boolean canEditStories,

        @JsonProperty("can_delete_stories")
        boolean canDeleteStories,

        @JsonProperty("can_manage_topics")
        boolean canManageTopics,

        @JsonProperty("custom_title")
        @Nullable
        String customTitle
) implements ChatMember {

    @Override
    public String status() {
        return "administrator";
    }

}
