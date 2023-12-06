package org.teleight.teleightbots.api.methods.updating;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record DeleteMessage(
        @JsonProperty(value = "chat_id", required = true)
        String chatId,

        @JsonProperty(value = "message_id",required = true)
        int messageId
) implements ApiMethodBoolean {

    public static @NotNull DeleteMessage of(String chatId, int messageId){
        return new DeleteMessage(chatId, messageId);
    }

    public static @NotNull Builder builder() {
        return new BuilderImpl();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "deleteMessage";
    }

    public sealed interface Builder permits BuilderImpl {
        @NotNull Builder chatId(String chatId);

        @NotNull Builder chatId(int chatId);

        @NotNull Builder messageId(int messageId);

        @NotNull DeleteMessage build();
    }

    static final class BuilderImpl implements Builder {
        private String chatId;
        private int messageId;

        @Override
        public @NotNull Builder chatId(String chatId) {
            this.chatId = chatId;
            return this;
        }

        @Override
        public @NotNull Builder chatId(int chatId) {
            return chatId("" + chatId);
        }

        @Override
        public @NotNull Builder messageId(int messageId) {
            this.messageId = messageId;
            return this;
        }

        @Override
        public @NotNull DeleteMessage build() {
            return new DeleteMessage(chatId, messageId);
        }
    }

}
