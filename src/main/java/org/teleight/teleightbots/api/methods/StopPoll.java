package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.Poll;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public record StopPoll(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_id", required = true)
        int messageId,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements ApiMethod<Poll> {

    public static Builder ofBuilder(@NotNull String chatId, int messageId) {
        return new StopPoll.Builder(chatId, messageId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "stopPoll";
    }

    @Override
    public @NotNull Poll deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Poll.class);
    }

    public static class Builder {
        private final String chatId;
        private final int messageId;
        private ReplyKeyboard replyMarkup;

        Builder(@NotNull String chatId, int messageId) {
            this.chatId = chatId;
            this.messageId = messageId;
        }

        public Builder replyMarkup(ReplyKeyboard replyMarkup) {
            this.replyMarkup = replyMarkup;
            return this;
        }

        public StopPoll build() {
            return new StopPoll(this.chatId, this.messageId, this.replyMarkup);
        }
    }
}
