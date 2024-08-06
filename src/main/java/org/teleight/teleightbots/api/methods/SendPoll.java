package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.InputPollOption;
import org.teleight.teleightbots.api.objects.MessageEntity;
import org.teleight.teleightbots.api.objects.ParseMode;
import org.teleight.teleightbots.api.objects.Poll;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;
import org.teleight.teleightbots.api.objects.ReplyParameters;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SendPoll(
        @JsonProperty(value = "business_connection_id")
        @Nullable
        String businessConnectionId,

        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id")
        int messageThreadId,

        @JsonProperty(value = "question", required = true)
        @NotNull
        String question,

        @JsonProperty("question_parse_mode")
        @Nullable
        ParseMode questionParseMode,

        @JsonProperty("question_entities")
        @Nullable
        MessageEntity[] questionEntities,

        @JsonProperty(value = "options", required = true)
        @NotNull
        InputPollOption[] options,

        @JsonProperty(value = "is_anonymous")
        boolean isAnonymous,

        @JsonProperty(value = "type")
        @Nullable
        String type,

        @JsonProperty(value = "allows_multiple_answers")
        boolean allowsMultipleAnswers,

        @JsonProperty(value = "correct_option_id")
        int correctOptionId,

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
        int openPeriod,

        @JsonProperty(value = "close_date")
        int closeDate,

        @JsonProperty(value = "is_closed")
        boolean isClosed,

        @JsonProperty(value = "disable_notification")
        boolean disableNotification,

        @JsonProperty(value = "protect_content")
        boolean protectContent,

        @JsonProperty(value = "message_effect_id")
        String messageEffectId,

        @JsonProperty(value = "reply_parameters")
        @Nullable
        ReplyParameters replyParameters,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements ApiMethod<Poll> {

    public static @NotNull Builder ofBuilder(String chatId, String question, InputPollOption[] options) {
        return new SendPoll.Builder().chatId(chatId).question(question).options(options);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendPoll";
    }

    @Override
    public @NotNull Poll deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Poll.class);
    }

}
