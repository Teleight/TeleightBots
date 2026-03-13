package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.MultiPartApiMethodMessage;
import org.teleight.teleightbots.api.objects.InputFile;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;
import org.teleight.teleightbots.api.objects.ReplyParameters;
import org.teleight.teleightbots.api.objects.SuggestedPostParameters;

import java.util.HashMap;
import java.util.Map;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SendVideoNote(
        @JsonProperty(value = "business_connection_id")
        @Nullable
        String businessConnectionId,

        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id")
        int messageThreadId,

        @JsonProperty(value = "direct_messages_topic_id")
        long directMessagesTopicId,

        @JsonProperty(value = "video_note", required = true)
        @NotNull
        InputFile videoNote,

        @JsonProperty(value = "duration")
        int duration,

        @JsonProperty(value = "length")
        int length,

        @JsonProperty(value = "thumbnail")
        @Nullable
        InputFile thumbnail,

        @JsonProperty(value = "disable_notification")
        boolean disableNotification,

        @JsonProperty(value = "protect_content")
        boolean protectContent,

        @JsonProperty(value = "allow_paid_broadcast")
        boolean allowPaidBroadcast,

        @JsonProperty(value = "message_effect_id")
        String messageEffectId,

        @JsonProperty(value = "suggested_post_parameters")
        @Nullable
        SuggestedPostParameters suggestedPostParameters,

        @JsonProperty(value = "reply_parameters")
        @Nullable
        ReplyParameters replyParameters,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements MultiPartApiMethodMessage {

    public static @NotNull Builder ofBuilder(String chatId, InputFile videoNote) {
        return new SendVideoNote.Builder().chatId(chatId).videoNote(videoNote);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendVideoNote";
    }

    @Override
    public @NotNull Map<String, Object> getParameters() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("business_connection_id", businessConnectionId);
        parameters.put("chat_id", chatId);
        parameters.put("message_thread_id", messageThreadId);
        parameters.put("direct_messages_topic_id", directMessagesTopicId);
        parameters.put("duration", duration);
        parameters.put("length", length);
        parameters.put("disable_notification", disableNotification);
        parameters.put("video_note", videoNote);
        parameters.put("thumbnail", thumbnail);
        parameters.put("protect_content", protectContent);
        parameters.put("allow_paid_broadcast", allowPaidBroadcast);
        parameters.put("message_effect_id", messageEffectId);
        parameters.put("suggested_post_parameters", suggestedPostParameters);
        parameters.put("reply_parameters", replyParameters);
        parameters.put("reply_markup", replyMarkup);
        return parameters;
    }

}
