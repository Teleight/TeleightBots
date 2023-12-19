package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodMessage;
import org.teleight.teleightbots.api.objects.entities.MessageEntity;
import org.teleight.teleightbots.api.objects.keyboard.ReplyKeyboard;
import org.teleight.teleightbots.api.utils.ParseMode;

public record SendMessage(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty("message_thread_id")
        @Nullable
        Integer messageThreadId,

        @JsonProperty(value = "text", required = true)
        @NotNull
        String text,

        @JsonProperty("parse_mode")
        @Nullable
        ParseMode parseMode,

        @JsonProperty("entities")
        @Nullable
        MessageEntity[] entities,

        @JsonProperty("disable_web_page_preview")
        Boolean disableWebPagePreview,

        @JsonProperty("disable_notification")
        Boolean disableNotification,

        @JsonProperty("protect_content")
        Boolean protectContent,

        @JsonProperty("reply_to_message_id")
        @Nullable
        Integer replyToMessageId,

        @JsonProperty(value = "allow_sending_without_reply")
        Boolean allowSendingWithoutReply,

        @JsonProperty("reply_markup")
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

        default @NotNull Builder chatId(long chatId) {
            return chatId("" + chatId);
        }

        @NotNull Builder messageThreadId(@Nullable Integer messageThreadId);

        @NotNull Builder text(@NotNull String text);

        @NotNull Builder parseMode(@Nullable ParseMode parseMode);

        @NotNull Builder entities(@Nullable MessageEntity[] entities);

        @NotNull Builder disableWebPagePreview(Boolean disableWebPagePreview);

        @NotNull Builder disableNotification(Boolean disableNotification);

        @NotNull Builder protectContent(Boolean protectContent);

        @NotNull Builder replyToMessageId(@Nullable Integer replyToMessageId);

        @NotNull Builder allowSendingWithoutReply(Boolean allowSendingWithoutReply);

        @NotNull Builder replyMarkup(@Nullable ReplyKeyboard replyMarkup);

        @NotNull SendMessage build();
    }

    static final class BuilderImpl implements Builder {
        private String chatId;
        private Integer messageThreadId;
        private String text;
        private ParseMode parseMode;
        private MessageEntity[] entities;
        private boolean disableWebPagePreview;
        private boolean disableNotification;
        private boolean protectContent;
        private Integer replyToMessageId;
        private boolean allowSendingWithoutReply;
        private ReplyKeyboard replyMarkup;

        @Override
        public @NotNull Builder chatId(@NotNull String chatId) {
            this.chatId = chatId;
            return this;
        }

        @Override
        public @NotNull Builder messageThreadId(@Nullable Integer messageThreadId) {
            this.messageThreadId = messageThreadId;
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
        public @NotNull Builder entities(@Nullable MessageEntity[] entities) {
            this.entities = entities;
            return this;
        }

        @Override
        public @NotNull Builder disableWebPagePreview(Boolean disableWebPagePreview) {
            this.disableWebPagePreview = disableWebPagePreview;
            return this;
        }

        @Override
        public @NotNull Builder disableNotification(Boolean disableNotification) {
            this.disableNotification = disableNotification;
            return this;
        }

        @Override
        public @NotNull Builder protectContent(Boolean protectContent) {
            this.protectContent = protectContent;
            return this;
        }

        @Override
        public @NotNull Builder replyMarkup(@Nullable ReplyKeyboard replyMarkup) {
            this.replyMarkup = replyMarkup;
            return this;
        }

        @Override
        public @NotNull Builder replyToMessageId(@Nullable Integer replyToMessageId) {
            this.replyToMessageId = replyToMessageId;
            return this;
        }

        @Override
        public @NotNull Builder allowSendingWithoutReply(Boolean allowSendingWithoutReply) {
            this.allowSendingWithoutReply = allowSendingWithoutReply;
            return this;
        }

        @Override
        public @NotNull SendMessage build() {
            return new SendMessage(
                    chatId,
                    messageThreadId,
                    text,
                    parseMode,
                    entities,
                    disableWebPagePreview,
                    disableNotification,
                    protectContent,
                    replyToMessageId,
                    allowSendingWithoutReply,
                    replyMarkup
            );
        }
    }

}
