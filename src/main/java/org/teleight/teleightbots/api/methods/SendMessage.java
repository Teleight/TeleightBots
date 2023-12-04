package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodMessage;
import org.teleight.teleightbots.api.objects.keyboard.KeyboardDeserializer;
import org.teleight.teleightbots.api.objects.keyboard.ReplyKeyboard;
import org.teleight.teleightbots.api.utils.ParseMode;

public record SendMessage(
        @JsonProperty("chat_id")
        @NotNull
        String chatId,

        @JsonProperty("text")
        @NotNull
        String text,

        @JsonProperty("parse_mode")
        @Nullable
        ParseMode parseMode,

        @JsonProperty("disable_notification")
        boolean disableNotification,

        @JsonProperty("reply_markup")
        @JsonDeserialize(using = KeyboardDeserializer.class)
        @Nullable
        ReplyKeyboard replyMarkup
) implements ApiMethodMessage {

    public static @NotNull Builder builder() {
        return new BuilderImpl();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendMessage";
    }

    public sealed interface Builder permits BuilderImpl {
        @NotNull Builder chatId(@NotNull String chatId);

        @NotNull Builder text(@NotNull String text);

        @NotNull Builder parseMode(@Nullable ParseMode parseMode);

        @NotNull Builder disableNotification(boolean disableNotification);

        @NotNull Builder replyMarkup(@Nullable ReplyKeyboard replyMarkup);

        @NotNull SendMessage build();
    }

    static final class BuilderImpl implements Builder {
        private String chatId;
        private String text;
        private ParseMode parseMode;
        private boolean disableNotification;
        private ReplyKeyboard replyMarkup;

        @Override
        public @NotNull Builder chatId(@NotNull String chatId) {
            this.chatId = chatId;
            return this;
        }

        @Override
        public @NotNull Builder text(@NotNull String text) {
            this.text = text;
            return this;
        }

        @Override
        public @NotNull Builder parseMode(@Nullable ParseMode parseMode) {
            this.parseMode = parseMode;
            return this;
        }

        @Override
        public @NotNull Builder disableNotification(boolean disableNotification) {
            this.disableNotification = disableNotification;
            return this;
        }

        @Override
        public @NotNull Builder replyMarkup(@Nullable ReplyKeyboard replyMarkup) {
            this.replyMarkup = replyMarkup;
            return this;
        }

        @Override
        public @NotNull SendMessage build() {
            return new SendMessage(chatId, text, parseMode, disableNotification, replyMarkup);
        }
    }

}
