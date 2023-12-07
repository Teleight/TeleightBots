package org.teleight.teleightbots.api.methods.inline;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodMessage;
import org.teleight.teleightbots.api.objects.keyboard.ReplyKeyboard;

public record EditMessageReplyMarkup(
        @JsonProperty("chat_id")
        @Nullable
        String chatId,

        @JsonProperty("message_id")
        @Nullable
        Integer messageId,

        @JsonProperty("inline_message_id")
        @Nullable
        String inlineMessageId,

        @JsonProperty("reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements ApiMethodMessage {

    public static @NotNull Builder builder() {
        return new BuilderImpl();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "editMessageReplyMarkup";
    }

    public sealed interface Builder permits BuilderImpl {
        @NotNull Builder chatId(@NotNull String chatId);

        @NotNull Builder messageId(int messageId);

        @NotNull Builder inlineMessageId(@NotNull String inlineMessageId);

        @NotNull Builder replyMarkup(@NotNull ReplyKeyboard replyMarkup);

        @NotNull EditMessageReplyMarkup build();
    }

    static final class BuilderImpl implements Builder {
        private String chatId;
        private int messageId;
        private String inlineMessageId;
        private ReplyKeyboard replyMarkup;

        @Override
        public @NotNull Builder chatId(@NotNull String chatId) {
            this.chatId = chatId;
            return this;
        }

        @Override
        public @NotNull Builder messageId(int messageId) {
            this.messageId = messageId;
            return this;
        }

        @Override
        public @NotNull Builder inlineMessageId(@NotNull String inlineMessageId) {
            this.inlineMessageId = inlineMessageId;
            return this;
        }

        @Override
        public @NotNull Builder replyMarkup(@NotNull ReplyKeyboard replyMarkup) {
            this.replyMarkup = replyMarkup;
            return this;
        }

        @Override
        public @NotNull EditMessageReplyMarkup build() {
            return new EditMessageReplyMarkup(chatId, messageId, inlineMessageId, replyMarkup);
        }
    }

}
