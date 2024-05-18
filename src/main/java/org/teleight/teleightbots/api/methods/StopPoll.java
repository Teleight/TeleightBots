package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.InlineKeyboardMarkup;
import org.teleight.teleightbots.api.objects.Poll;

public record StopPoll(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_id", required = true)
        int messageId,

        @JsonProperty(value = "reply_markup")
        @Nullable
        InlineKeyboardMarkup replyMarkup
) implements ApiMethod<Poll> {

    public static Builder ofBuilder(@NotNull String chatId, int messageId) {
        return new StopPoll.Builder(chatId, messageId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "stopPoll";
    }

    public static class Builder {
        private final String chatId;
        private final int messageId;
        private InlineKeyboardMarkup replyMarkup;

        Builder(@NotNull String chatId, int messageId) {
            this.chatId = chatId;
            this.messageId = messageId;
        }

        public Builder replyMarkup(InlineKeyboardMarkup replyMarkup) {
            this.replyMarkup = replyMarkup;
            return this;
        }

        public StopPoll build() {
            return new StopPoll(this.chatId, this.messageId, this.replyMarkup);
        }
    }
}
