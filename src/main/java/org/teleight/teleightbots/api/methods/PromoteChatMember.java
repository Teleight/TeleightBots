package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

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

    public static Builder ofBuilder(String chatId, long userId) {
        return new PromoteChatMember.Builder(chatId, userId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "promoteChatMember";
    }

    public static class Builder {
        private final String chatId;
        private final long userId;
        private boolean isAnonymous;
        private boolean canManageChat;
        private boolean canDeleteMessages;
        private boolean canManageVideoChats;
        private boolean canRestrictMembers;
        private boolean canPromoteMembers;
        private boolean canChangeInfo;
        private boolean canInviteUsers;
        private boolean canPostMessages;
        private boolean canEditMessages;
        private boolean canPinMessages;
        private boolean canPostStories;
        private boolean canEditStories;
        private boolean canDeleteStories;
        private boolean canManageTopics;

        Builder(String chatId, long userId) {
            this.chatId = chatId;
            this.userId = userId;
        }

        public Builder isAnonymous(boolean isAnonymous) {
            this.isAnonymous = isAnonymous;
            return this;
        }

        public Builder canManageChat(boolean canManageChat) {
            this.canManageChat = canManageChat;
            return this;
        }

        public Builder canDeleteMessages(boolean canDeleteMessages) {
            this.canDeleteMessages = canDeleteMessages;
            return this;
        }

        public Builder canManageVideoChats(boolean canManageVideoChats) {
            this.canManageVideoChats = canManageVideoChats;
            return this;
        }

        public Builder canRestrictMembers(boolean canRestrictMembers) {
            this.canRestrictMembers = canRestrictMembers;
            return this;
        }

        public Builder canPromoteMembers(boolean canPromoteMembers) {
            this.canPromoteMembers = canPromoteMembers;
            return this;
        }

        public Builder canChangeInfo(boolean canChangeInfo) {
            this.canChangeInfo = canChangeInfo;
            return this;
        }

        public Builder canInviteUsers(boolean canInviteUsers) {
            this.canInviteUsers = canInviteUsers;
            return this;
        }

        public Builder canPostMessages(boolean canPostMessages) {
            this.canPostMessages = canPostMessages;
            return this;
        }

        public Builder canEditMessages(boolean canEditMessages) {
            this.canEditMessages = canEditMessages;
            return this;
        }

        public Builder canPinMessages(boolean canPinMessages) {
            this.canPinMessages = canPinMessages;
            return this;
        }

        public Builder canPostStories(boolean canPostStories) {
            this.canPostStories = canPostStories;
            return this;
        }

        public Builder canEditStories(boolean canEditStories) {
            this.canEditStories = canEditStories;
            return this;
        }

        public Builder canDeleteStories(boolean canDeleteStories) {
            this.canDeleteStories = canDeleteStories;
            return this;
        }

        public Builder canManageTopics(boolean canManageTopics) {
            this.canManageTopics = canManageTopics;
            return this;
        }

        public PromoteChatMember build() {
            return new PromoteChatMember(this.chatId, this.userId, this.isAnonymous, this.canManageChat, this.canDeleteMessages, this.canManageVideoChats, this.canRestrictMembers, this.canPromoteMembers, this.canChangeInfo, this.canInviteUsers, this.canPostMessages, this.canEditMessages, this.canPinMessages, this.canPostStories, this.canEditStories, this.canDeleteStories, this.canManageTopics);
        }
    }
}
