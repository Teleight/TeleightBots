package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.MultiPartApiMethodMessage;
import org.teleight.teleightbots.api.objects.InputPollMedia;
import org.teleight.teleightbots.api.objects.InputPollOption;
import org.teleight.teleightbots.api.objects.MessageEntity;
import org.teleight.teleightbots.api.objects.ParseMode;
import org.teleight.teleightbots.api.objects.PollType;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;
import org.teleight.teleightbots.api.objects.ReplyParameters;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

        @JsonProperty(value = "question_parse_mode")
        @Nullable
        ParseMode questionParseMode,

        @JsonProperty(value = "question_entities")
        @Nullable
        MessageEntity[] questionEntities,

        @JsonProperty(value = "options", required = true)
        @NotNull
        InputPollOption[] options,

        @JsonProperty(value = "is_anonymous")
        boolean isAnonymous,

        @JsonProperty(value = "type")
        @Nullable
        PollType type,

        @JsonProperty(value = "allows_multiple_answers")
        boolean allowsMultipleAnswers,

        @JsonProperty(value = "allows_revoting")
        boolean allowsRevoting,

        @JsonProperty(value = "shuffle_options")
        boolean shuffleOptions,

        @JsonProperty(value = "allow_adding_options")
        boolean allowAddingOptions,

        @JsonProperty(value = "hide_results_until_closes")
        boolean hideResultsUntilCloses,

        @JsonProperty(value = "members_only")
        boolean membersOnly,

        @JsonProperty(value = "country_codes")
        @Nullable
        String[] countryCodes,

        @JsonProperty(value = "correct_option_ids")
        int[] correctOptionIds,

        @JsonProperty(value = "explanation")
        @Nullable
        String explanation,

        @JsonProperty(value = "explanation_parse_mode")
        @Nullable
        ParseMode explanationParseMode,

        @JsonProperty(value = "explanation_entities")
        @Nullable
        MessageEntity[] explanationEntities,

        @JsonProperty(value = "explanation_media")
        @Nullable
        InputPollMedia explanationMedia,

        @JsonProperty(value = "open_period")
        int openPeriod,

        @JsonProperty(value = "close_date")
        int closeDate,

        @JsonProperty(value = "is_closed")
        boolean isClosed,

        @JsonProperty(value = "description")
        @Nullable
        String description,

        @JsonProperty(value = "description_parse_mode")
        @Nullable
        ParseMode descriptionParseMode,

        @JsonProperty(value = "description_entities")
        @Nullable
        MessageEntity[] descriptionEntities,

        @JsonProperty(value = "media")
        @Nullable
        InputPollMedia media,

        @JsonProperty(value = "disable_notification")
        boolean disableNotification,

        @JsonProperty(value = "protect_content")
        boolean protectContent,

        @JsonProperty(value = "allow_paid_broadcast")
        boolean allowPaidBroadcast,

        @JsonProperty(value = "message_effect_id")
        String messageEffectId,

        @JsonProperty(value = "reply_parameters")
        @Nullable
        ReplyParameters replyParameters,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements MultiPartApiMethodMessage {

    public static @NotNull Builder ofBuilder(String chatId, String question, InputPollOption[] options) {
        return new SendPoll.Builder().chatId(chatId).question(question).options(options);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendPoll";
    }

    @Override
    public @NotNull Map<String, Object> getParameters() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("business_connection_id", businessConnectionId);
        parameters.put("chat_id", chatId);
        parameters.put("message_thread_id", messageThreadId);
        parameters.put("question", question);
        parameters.put("question_parse_mode", questionParseMode);
        parameters.put("question_entities", questionEntities);
        parameters.put("options", options);
        parameters.put("is_anonymous", isAnonymous);
        parameters.put("type", type);
        parameters.put("allows_multiple_answers", allowsMultipleAnswers);
        parameters.put("allows_revoting", allowsRevoting);
        parameters.put("shuffle_options", shuffleOptions);
        parameters.put("allow_adding_options", allowAddingOptions);
        parameters.put("hide_results_until_closes", hideResultsUntilCloses);
        parameters.put("members_only", membersOnly);
        parameters.put("country_codes", countryCodes);
        parameters.put("correct_option_ids", correctOptionIds);
        parameters.put("explanation", explanation);
        parameters.put("explanation_parse_mode", explanationParseMode);
        parameters.put("explanation_entities", explanationEntities);
        parameters.put("explanation_media", explanationMedia);
        parameters.put("open_period", openPeriod);
        parameters.put("close_date", closeDate);
        parameters.put("is_closed", isClosed);
        parameters.put("description", description);
        parameters.put("description_parse_mode", descriptionParseMode);
        parameters.put("description_entities", descriptionEntities);
        parameters.put("media", media);
        parameters.put("disable_notification", disableNotification);
        parameters.put("protect_content", protectContent);
        parameters.put("allow_paid_broadcast", allowPaidBroadcast);
        parameters.put("message_effect_id", messageEffectId);
        parameters.put("reply_parameters", replyParameters);
        parameters.put("reply_markup", replyMarkup);
        return parameters;
    }
}
