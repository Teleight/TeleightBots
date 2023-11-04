package org.teleight.teleightbots.api.methods.inline;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodMessage;
import org.teleight.teleightbots.api.objects.keyboard.KeyboardDeserializer;
import org.teleight.teleightbots.api.objects.keyboard.ReplyKeyboard;

public record EditMessageText(
        @JsonProperty("chat_id")
        @Nullable
        String chatId,

        @JsonProperty("text")
        @NotNull
        String text,

        @JsonProperty("message_id")
        int messageId,

        @JsonProperty("reply_markup")
        @JsonDeserialize(using = KeyboardDeserializer.class)
        @Nullable
        ReplyKeyboard replyMarkup
) implements ApiMethodMessage {

    public static Builder builder() {
        return new BuilderImpl();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "editMessageText";
    }

    public sealed interface Builder permits BuilderImpl {
        Builder chatId(String chatId);

        Builder text(String text);

        Builder messageId(int messageId);

        Builder replyMarkup(ReplyKeyboard replyMarkup);

        EditMessageText build();
    }

    static final class BuilderImpl implements Builder {
        private String chatId;
        private String text;
        private int messageId;
        private ReplyKeyboard replyMarkup;

        @Override
        public Builder chatId(String chatId) {
            this.chatId = chatId;
            return this;
        }

        @Override
        public Builder text(String text) {
            this.text = text;
            return this;
        }

        @Override
        public Builder messageId(int messageId) {
            this.messageId = messageId;
            return this;
        }

        @Override
        public Builder replyMarkup(ReplyKeyboard replyMarkup) {
            this.replyMarkup = replyMarkup;
            return this;
        }

        @Override
        public EditMessageText build() {
            return new EditMessageText(chatId, text, messageId, replyMarkup);
        }
    }

}
