package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodSerializable;
import org.teleight.teleightbots.api.objects.InlineKeyboardMarkup;
import org.teleight.teleightbots.api.objects.Message;

import java.io.Serializable;
import java.util.List;

public record StopMessageLiveLocation(
        @JsonProperty(value = "chat_id")
        @Nullable
        String chatId,

        @JsonProperty(value = "message_id")
        int messageId,

        @JsonProperty(value = "inline_message_id")
        @Nullable
        String inlineMessageId,

        @JsonProperty(value = "reply_markup")
        @Nullable
        InlineKeyboardMarkup replyMarkup
) implements ApiMethodSerializable {

    public static Builder of() {
        return new StopMessageLiveLocation.Builder();
    }

    @Override
    public List<Class<? extends Serializable>> getSerializableClasses() {
        return List.of(Message.class, Boolean.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "stopMessageLiveLocation";
    }

    public static class Builder {
        private String chatId;
        private int messageId;
        private String inlineMessageId;
        private InlineKeyboardMarkup replyMarkup;

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

        public Builder replyMarkup(InlineKeyboardMarkup replyMarkup) {
            this.replyMarkup = replyMarkup;
            return this;
        }

        public StopMessageLiveLocation build() {
            return new StopMessageLiveLocation(this.chatId, this.messageId, this.inlineMessageId, this.replyMarkup);
        }

    }
}
