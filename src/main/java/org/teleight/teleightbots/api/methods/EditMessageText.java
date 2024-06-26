package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodMultiResponse;
import org.teleight.teleightbots.api.objects.LinkPreviewOptions;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.MessageEntity;
import org.teleight.teleightbots.api.objects.ParseMode;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;

import java.io.Serializable;
import java.util.List;

public record EditMessageText(
        @JsonProperty(value = "chat_id")
        @Nullable
        String chatId,

        @JsonProperty(value = "message_id")
        int messageId,

        @JsonProperty(value = "inline_message_id")
        @Nullable
        String inlineMessageId,

        @JsonProperty(value = "text", required = true)
        @NotNull
        String text,

        @JsonProperty(value = "parse_mode")
        @Nullable
        ParseMode parseMode,

        @JsonProperty(value = "entities")
        @Nullable
        MessageEntity[] entities,

        @JsonProperty(value = "link_preview_options")
        @Nullable
        LinkPreviewOptions linkPreviewOptions,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements ApiMethodMultiResponse {

    public static Builder ofBuilder(String text) {
        return new EditMessageText.Builder(text);
    }

    @Override
    public List<Class<? extends Serializable>> getSerializableClasses() {
        return List.of(Message.class, Boolean.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "editMessageText";
    }

    public static class Builder {
        private String chatId;
        private int messageId;
        private String inlineMessageId;
        private final String text;
        private ParseMode parseMode;
        private MessageEntity[] entities;
        private LinkPreviewOptions linkPreviewOptions;
        private ReplyKeyboard replyMarkup;

        Builder(String text) {
            this.text = text;
        }

        public Builder chatId(String chatId) {
            this.chatId = chatId;
            return this;
        }

        public Builder messageId(int messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder inlineMessageId(String inlineMessageId) {
            this.inlineMessageId = inlineMessageId;
            return this;
        }

        public Builder parseMode(ParseMode parseMode) {
            this.parseMode = parseMode;
            return this;
        }

        public Builder entities(MessageEntity[] entities) {
            this.entities = entities;
            return this;
        }

        public Builder linkPreviewOptions(LinkPreviewOptions linkPreviewOptions) {
            this.linkPreviewOptions = linkPreviewOptions;
            return this;
        }

        public Builder replyMarkup(ReplyKeyboard replyMarkup) {
            this.replyMarkup = replyMarkup;
            return this;
        }

        public EditMessageText build() {
            return new EditMessageText(this.chatId, this.messageId, this.inlineMessageId, this.text, this.parseMode, this.entities, this.linkPreviewOptions, this.replyMarkup);
        }

    }

}
