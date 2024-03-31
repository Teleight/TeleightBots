package org.teleight.teleightbots.api.methods.chat.admin.moderate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder
public record PromoteChatMember(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "user_id", required = true)
        @NotNull
        Long userId,

        @JsonProperty(value = "is_anonymous")
        @Nullable
        Boolean isAnonymous,

        @JsonProperty(value = "can_manage_chat")
        @Nullable
        Boolean canManageChat,

        @JsonProperty(value = "can_delete_messages")
        @Nullable
        Boolean canDeleteMessages,

        @JsonProperty(value = "can_manage_video_chats")
        @Nullable
        Boolean canManageVideoChats,

        @JsonProperty(value = "can_restrict_members")
        @Nullable
        Boolean canRestrictMembers,

        @JsonProperty(value = "can_promote_members")
        @Nullable
        Boolean canPromoteMembers,

        @JsonProperty(value = "can_change_info")
        @Nullable
        Boolean canChangeInfo,

        @JsonProperty(value = "can_invite_users")
        @Nullable
        Boolean canInviteUsers,

        @JsonProperty(value = "can_post_messages")
        @Nullable
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
) implements ApiMethodBoolean {
    @Override
    public @NotNull String getEndpointURL() {
        return "promoteChatMember";
    }

    public static class PromoteChatMemberBuilder {
        @Tolerate
        public PromoteChatMemberBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
