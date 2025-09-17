package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record ChatAdministratorRights(
        @JsonProperty(value = "is_anonymous", required = true)
        boolean isAnonymous,

        @JsonProperty(value = "can_manage_chat", required = true)
        boolean canManageChat,

        @JsonProperty(value = "can_delete_messages", required = true)
        boolean canDeleteMessages,

        @JsonProperty(value = "can_manage_video_chats", required = true)
        boolean canManageVideoChats,

        @JsonProperty(value = "can_restrict_members", required = true)
        boolean canRestrictMembers,

        @JsonProperty(value = "can_promote_members", required = true)
        boolean canPromoteMembers,

        @JsonProperty(value = "can_change_info", required = true)
        boolean canChangeInfo,

        @JsonProperty(value = "can_invite_users", required = true)
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
        boolean canManageDirectMessages
) implements ApiResult {

}
