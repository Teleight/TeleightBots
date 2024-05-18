package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record ChatAdministratorRights(
        @JsonProperty(value = "is_anonymous", required = true)
        Boolean isAnonymous,

        @JsonProperty(value = "can_manage_chat", required = true)
        Boolean canManageChat,

        @JsonProperty(value = "can_delete_messages", required = true)
        Boolean canDeleteMessages,

        @JsonProperty(value = "can_manage_video_chats", required = true)
        Boolean canManageVideoChats,

        @JsonProperty(value = "can_restrict_members", required = true)
        Boolean canRestrictMembers,

        @JsonProperty(value = "can_promote_members", required = true)
        Boolean canPromoteMembers,

        @JsonProperty(value = "can_change_info", required = true)
        Boolean canChangeInfo,

        @JsonProperty(value = "can_invite_users", required = true)
        Boolean canInviteUsers,

        @JsonProperty(value = "can_post_messages")
        Boolean canPostMessages,

        @JsonProperty(value = "can_edit_messages")
        @Nullable
        Boolean canEditMessages,

        @JsonProperty(value = "can_pin_messages")
        @Nullable
        Boolean canPinMessages,

        @JsonProperty(value = "can_post_stories")
        @Nullable
        Boolean canPostStories,

        @JsonProperty(value = "can_edit_stories")
        @Nullable
        Boolean canEditStories,

        @JsonProperty(value = "can_delete_stories")
        @Nullable
        Boolean canDeleteStories,

        @JsonProperty(value = "can_manage_topics")
        @Nullable
        Boolean canManageTopics
) implements ApiResult {

}
