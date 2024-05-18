package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodSerializable;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;

import java.io.Serializable;
import java.util.List;

public record EditMessageReplyMarkup(
        @JsonProperty("chat_id")
        @Nullable
        String chatId,

        @JsonProperty("message_id")
        int messageId,

        @JsonProperty("inline_message_id")
        @Nullable
        String inlineMessageId,

        @JsonProperty("reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements ApiMethodSerializable {

    public static @NotNull Builder ofBuilder() {
        return new EditMessageReplyMarkup.Builder();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "editMessageReplyMarkup";
    }

    @Override
    public List<Class<? extends Serializable>> getSerializableClasses() {
        return List.of(Message.class, Boolean.class);
    }

    public static class Builder {
        private String chatId;
        private int messageId;
        private String inlineMessageId;
        private ReplyKeyboard replyMarkup;

        public Builder chatId(@NotNull String chatId) {
            this.chatId = chatId;
            return this;
        }

        public Builder messageId(int messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder inlineMessageId(@NotNull String inlineMessageId) {
            this.inlineMessageId = inlineMessageId;
            return this;
        }

        public Builder replyMarkup(@NotNull ReplyKeyboard replyMarkup) {
            this.replyMarkup = replyMarkup;
            return this;
        }

        public EditMessageReplyMarkup build() {
            return new EditMessageReplyMarkup(this.chatId, this.messageId, this.inlineMessageId, this.replyMarkup);
        }
    }

}
