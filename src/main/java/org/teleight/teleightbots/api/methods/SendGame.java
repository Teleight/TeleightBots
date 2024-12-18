package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodMessage;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;
import org.teleight.teleightbots.api.objects.ReplyParameters;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SendGame(
        @JsonProperty(value = "business_connection_id")
        @Nullable
        String businessConnectionId,

        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id")
        int messageThreadId,

        @JsonProperty(value = "game_short_name", required = true)
        @NotNull
        String gameShortName,

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
) implements ApiMethodMessage {

    public static @NotNull Builder ofBuilder(String chatId, String gameShortName) {
        return new SendGame.Builder().chatId(chatId).gameShortName(gameShortName);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendGame";
    }

}
