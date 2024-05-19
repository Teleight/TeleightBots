package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.MessageEntity;
import org.teleight.teleightbots.api.objects.ParseMode;
import org.teleight.teleightbots.api.objects.Poll;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;
import org.teleight.teleightbots.api.objects.ReplyParameters;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

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

        @JsonProperty(value = "options", required = true)
        @NotNull
        String[] options,

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

        @JsonProperty(value = "reply_parameters")
        @Nullable
        ReplyParameters replyParameters,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements ApiMethod<Poll> {

    public static Builder ofBuilder(String chatId, String question, String[] options) {
        return new SendPoll.Builder(chatId, question, options);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendPoll";
    }

    @Override
    public @NotNull Poll deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Poll.class);
    }

    public static class Builder {
        private String businessConnectionId;
        private final String chatId;
        private int messageThreadId;
        private final String question;
        private final String[] options;
        private boolean isAnonymous;
        private String type;
        private boolean allowsMultipleAnswers;
        private int correctOptionId;
        private String explanation;
        private ParseMode explanationParseMode;
        private MessageEntity[] explanationEntities;
        private int openPeriod;
        private int closeDate;
        private boolean isClosed;
        private boolean disableNotification;
        private boolean protectContent;
        private ReplyParameters replyParameters;
        private ReplyKeyboard replyMarkup;

        Builder(String chatId, String question, String[] options) {
            this.chatId = chatId;
            this.question = question;
            this.options = options;
        }

        public Builder businessConnectionId(String businessConnectionId) {
            this.businessConnectionId = businessConnectionId;
            return this;
        }

        public Builder messageThreadId(int messageThreadId) {
            this.messageThreadId = messageThreadId;
            return this;
        }

        public Builder isAnonymous(boolean isAnonymous) {
            this.isAnonymous = isAnonymous;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder allowsMultipleAnswers(boolean allowsMultipleAnswers) {
            this.allowsMultipleAnswers = allowsMultipleAnswers;
            return this;
        }

        public Builder correctOptionId(int correctOptionId) {
            this.correctOptionId = correctOptionId;
            return this;
        }

        public Builder explanation(String explanation) {
            this.explanation = explanation;
            return this;
        }

        public Builder explanationParseMode(ParseMode explanationParseMode) {
            this.explanationParseMode = explanationParseMode;
            return this;
        }

        public Builder explanationEntities(MessageEntity[] explanationEntities) {
            this.explanationEntities = explanationEntities;
            return this;
        }

        public Builder openPeriod(int openPeriod) {
            this.openPeriod = openPeriod;
            return this;
        }

        public Builder closeDate(int closeDate) {
            this.closeDate = closeDate;
            return this;
        }

        public Builder isClosed(boolean isClosed) {
            this.isClosed = isClosed;
            return this;
        }

        public Builder disableNotification(boolean disableNotification) {
            this.disableNotification = disableNotification;
            return this;
        }

        public Builder protectContent(boolean protectContent) {
            this.protectContent = protectContent;
            return this;
        }

        public Builder replyParameters(ReplyParameters replyParameters) {
            this.replyParameters = replyParameters;
            return this;
        }

        public Builder replyMarkup(ReplyKeyboard replyMarkup) {
            this.replyMarkup = replyMarkup;
            return this;
        }

        public SendPoll build() {
            return new SendPoll(this.businessConnectionId, this.chatId, this.messageThreadId, this.question, this.options, this.isAnonymous, this.type, this.allowsMultipleAnswers, this.correctOptionId, this.explanation, this.explanationParseMode, this.explanationEntities, this.openPeriod, this.closeDate, this.isClosed, this.disableNotification, this.protectContent, this.replyParameters, this.replyMarkup);
        }
    }
}
