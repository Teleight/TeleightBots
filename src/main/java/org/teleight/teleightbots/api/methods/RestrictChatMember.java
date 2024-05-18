package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.ChatPermissions;

public record RestrictChatMember(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "permissions", required = true)
        @NotNull
        ChatPermissions permissions,

        @JsonProperty(value = "use_independent_chat_permissions")
        boolean useIndependentChatPermissions,

        @JsonProperty(value = "until_date")
        int untilDate
) implements ApiMethodBoolean {

    public static Builder ofBuilder(String chatId, long userId, ChatPermissions chatPermissions) {
        return new Builder(chatId, userId, chatPermissions);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "restrictChatMember";
    }

    public static class Builder {
        private final String chatId;
        private final long userId;
        private final ChatPermissions permissions;
        private boolean useIndependentChatPermissions;
        private int untilDate;

        Builder(String chatId, long userId, ChatPermissions permissions) {
            this.chatId = chatId;
            this.userId = userId;
            this.permissions = permissions;
        }

        public Builder useIndependentChatPermissions(boolean useIndependentChatPermissions) {
            this.useIndependentChatPermissions = useIndependentChatPermissions;
            return this;
        }

        public Builder untilDate(int untilDate) {
            this.untilDate = untilDate;
            return this;
        }

        public RestrictChatMember build() {
            return new RestrictChatMember(this.chatId, this.userId, this.permissions, this.useIndependentChatPermissions, this.untilDate);
        }
    }
}
