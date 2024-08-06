package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record PromoteChatMember(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "is_anonymous")
        boolean isAnonymous,

        @JsonProperty(value = "can_manage_chat")
        boolean canManageChat,

        @JsonProperty(value = "can_delete_messages")
        boolean canDeleteMessages,

        @JsonProperty(value = "can_manage_video_chats")
        boolean canManageVideoChats,

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
        boolean canManageTopics
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String chatId, long userId) {
        return new PromoteChatMember.Builder().chatId(chatId).userId(userId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "promoteChatMember";
    }

}
