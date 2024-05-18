package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.ChatPermissions;

public record SetChatPermissions(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "permissions", required = true)
        @NotNull
        ChatPermissions senderChatId,

        @JsonProperty(value = "use_independent_chat_permissions")
        boolean useIndependentChatPermissions
) implements ApiMethodBoolean {

    public static Builder ofBuilder(String chatId, ChatPermissions senderChatId) {
        return new Builder(chatId, senderChatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setChatPermissions";
    }

    public static class Builder {
        private final String chatId;
        private final ChatPermissions senderChatId;
        private boolean useIndependentChatPermissions;

        Builder(String chatId, ChatPermissions senderChatId) {
            this.chatId = chatId;
            this.senderChatId = senderChatId;
        }

        public Builder useIndependentChatPermissions(boolean useIndependentChatPermissions) {
            this.useIndependentChatPermissions = useIndependentChatPermissions;
            return this;
        }

        public SetChatPermissions build() {
            return new SetChatPermissions(this.chatId, this.senderChatId, this.useIndependentChatPermissions);
        }
    }
}
