package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodMessage;

public record ForwardMessage(
        @JsonProperty(value = "chat_id", required = true)
        String chatId,

        @JsonProperty(value = "from_chat_id", required = true)
        String fromChatId,

        @JsonProperty(value = "message_id", required = true)
        long messageId
) implements ApiMethodMessage {

    public static @NotNull Builder builder() {
        return new BuilderImpl();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "forwardMessage";
    }

    public sealed interface Builder permits BuilderImpl {
        @NotNull Builder chatId(@NotNull String chatId);

        default @NotNull Builder chatId(long chatId){
            return chatId("" + chatId);
        }

        @NotNull Builder fromChatId(@NotNull String fromChatId);

        default @NotNull Builder fromChatId(long fromChatId){
            return fromChatId("" + fromChatId);
        }

        @NotNull Builder messageId(long messageId);

        @NotNull ForwardMessage build();
    }

    static final class BuilderImpl implements Builder {
        private String chatId;
        private String fromChatId;
        private long messageId;

        @Override
        public @NotNull Builder chatId(@NotNull String chatId) {
            this.chatId = chatId;
            return this;
        }

        @Override
        public @NotNull Builder fromChatId(@NotNull String fromChatId) {
            this.fromChatId = fromChatId;
            return this;
        }

        @Override
        public @NotNull Builder messageId(long messageId) {
            this.messageId = messageId;
            return this;
        }

        @Override
        public @NotNull ForwardMessage build() {
            return new ForwardMessage(chatId, fromChatId, messageId);
        }
    }

}
