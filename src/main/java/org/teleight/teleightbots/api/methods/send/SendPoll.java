package org.teleight.teleightbots.api.methods.send;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.ReplyParameters;
import org.teleight.teleightbots.api.objects.entities.MessageEntity;
import org.teleight.teleightbots.api.objects.keyboard.ReplyKeyboard;
import org.teleight.teleightbots.api.objects.poll.Poll;
import org.teleight.teleightbots.api.utils.ParseMode;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder
public record SendPoll(
        @JsonProperty(value = "business_connection_id")
        @Nullable
        String businessConnectionId,

        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id")
        @Nullable
        Integer messageThreadId,

        @JsonProperty(value = "question", required = true)
        @NotNull
        String question,

        @JsonProperty(value = "options", required = true)
        @NotNull
        String[] options,

        @JsonProperty(value = "is_anonymous")
        @Nullable
        Boolean isAnonymous,

        @JsonProperty(value = "type")
        @Nullable
        String type,

        @JsonProperty(value = "allows_multiple_answers")
        @Nullable
        Boolean allowsMultipleAnswers,

        @JsonProperty(value = "correct_option_id")
        @Nullable
        Integer correctOptionId,

        @JsonProperty(value = "explanation")
        @Nullable
        String explanation,

        @JsonProperty(value = "explanation_parse_mode")
        @Nullable
        ParseMode explanationParseMode,

        @JsonProperty(value = "explanation_entities")
        @Nullable
        MessageEntity[] explanationEntities,

        @JsonProperty(value = "open_period")
        @Nullable
        Integer openPeriod,

        @JsonProperty(value = "close_date")
        @Nullable
        Integer closeDate,

        @JsonProperty(value = "is_closed")
        @Nullable
        Boolean isClosed,

        @JsonProperty(value = "disable_notification")
        @Nullable
        Boolean disableNotification,

        @JsonProperty(value = "protect_content")
        @Nullable
        Boolean protectContent,

        @JsonProperty(value = "reply_parameters")
        @Nullable
        ReplyParameters replyParameters,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements ApiMethod<Poll> {
    @Override
    public @NotNull Poll deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Poll.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendPoll";
    }

    public static class SendPollBuilder {
        @Tolerate
        public SendPollBuilder chatId(Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
