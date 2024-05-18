package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.ChatAction;

public record SendChatAction(
        @JsonProperty(value = "business_connection_id")
        @Nullable
        String businessConnectionId,

        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id")
        int messageThreadId,

        @JsonProperty(value = "action", required = true)
        @NotNull
        ChatAction action
) implements ApiMethodBoolean {

    public static Builder of(String chatId, ChatAction action) {
        return new SendChatAction.Builder(chatId, action);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendChatAction";
    }

    public static class Builder {
        private String businessConnectionId;
        private final String chatId;
        private int messageThreadId;
        private final ChatAction action;

        Builder(String chatId, ChatAction action) {
            this.chatId = chatId;
            this.action = action;
        }

        public Builder businessConnectionId(String businessConnectionId) {
            this.businessConnectionId = businessConnectionId;
            return this;
        }

        public Builder messageThreadId(int messageThreadId) {
            this.messageThreadId = messageThreadId;
            return this;
        }

        public SendChatAction build() {
            return new SendChatAction(this.businessConnectionId, this.chatId, this.messageThreadId, this.action);
        }
    }
}
