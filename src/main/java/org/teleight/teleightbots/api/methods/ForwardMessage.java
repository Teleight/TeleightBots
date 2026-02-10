package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodMessage;
import org.teleight.teleightbots.api.objects.SuggestedPostParameters;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record ForwardMessage(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id")
        int messageThreadId,

        @JsonProperty(value = "direct_messages_topic_id")
        long directMessagesTopicId,

        @JsonProperty(value = "from_chat_id", required = true)
        @NotNull
        String fromChatId,

        @JsonProperty(value = "video_start_timestamp")
        int videoStartTimestamp,

        @JsonProperty(value = "disable_notification")
        boolean disableNotification,

        @JsonProperty(value = "protect_content")
        boolean protectContent,

        @JsonProperty(value = "message_effect_id")
        @Nullable
        String messageEffectId,

        @JsonProperty(value = "suggested_post_parameters")
        @Nullable
        SuggestedPostParameters suggestedPostParameters,

        @JsonProperty(value = "message_id", required = true)
        int messageId
) implements ApiMethodMessage {

    public static @NotNull Builder ofBuilder(String chatId, String fromChatId, int messageId) {
        return new ForwardMessage.Builder().chatId(chatId).fromChatId(fromChatId).messageId(messageId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "forwardMessage";
    }

}
