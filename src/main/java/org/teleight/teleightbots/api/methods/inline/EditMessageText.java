package org.teleight.teleightbots.api.methods.inline;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodMessage;
import org.teleight.teleightbots.api.objects.keyboard.ReplyKeyboard;
import org.teleight.teleightbots.api.utils.ParseMode;

public record EditMessageText(
        @JsonProperty("chat_id")
        @Nullable
        String chatId,

        @JsonProperty("text")
        @NotNull
        String text,

        @JsonProperty("parse_mode")
        @Nullable
        ParseMode parseMode,

        @JsonProperty("message_id")
        int messageId,

        @JsonProperty("reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements ApiMethodMessage {

    public static @NotNull Builder builder() {
        return new BuilderImpl();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "editMessageText";
    }

    public sealed interface Builder permits BuilderImpl {
        @NotNull Builder chatId(String chatId);

        @NotNull Builder text(String text);

        @NotNull Builder parseMode(ParseMode parseMode);

        @NotNull Builder messageId(int messageId);

        @NotNull Builder replyMarkup(ReplyKeyboard replyMarkup);

        @NotNull EditMessageText build();
    }

    static final class BuilderImpl implements Builder {
        private String chatId;
        private String text;
        private ParseMode parseMode;
        private int messageId;
        private ReplyKeyboard replyMarkup;

        @Override
        public @NotNull Builder chatId(String chatId) {
            this.chatId = chatId;
            return this;
        }

        @Override
        public @NotNull Builder text(String text) {
            this.text = text;
            return this;
        }

        @Override
        public @NotNull Builder parseMode(ParseMode parseMode) {
            this.parseMode = parseMode;
            return this;
        }

        @Override
        public @NotNull Builder messageId(int messageId) {
            this.messageId = messageId;
            return this;
        }

        @Override
        public @NotNull Builder replyMarkup(ReplyKeyboard replyMarkup) {
            this.replyMarkup = replyMarkup;
            return this;
        }

        @Override
        public @NotNull EditMessageText build() {
            return new EditMessageText(chatId, text, parseMode, messageId, replyMarkup);
        }
    }

}
