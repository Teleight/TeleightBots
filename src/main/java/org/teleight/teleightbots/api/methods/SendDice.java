package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.Dice;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;
import org.teleight.teleightbots.api.objects.ReplyParameters;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SendDice(
        @JsonProperty("business_connection_id")
        @Nullable
        String businessConnectionId,

        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty("message_thread_id")
        int messageThreadId,

        @JsonProperty("emoji")
        @Nullable
        String emoji,

        @JsonProperty("disable_notification")
        boolean disableNotification,

        @JsonProperty("protect_content")
        boolean protectContent,

        @JsonProperty("message_effect_id")
        @Nullable
        String messageEffectId,

        @JsonProperty("reply_parameters")
        @Nullable
        ReplyParameters replyParameters,

        @JsonProperty("reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements ApiMethod<Dice> {

    public static @NotNull Builder ofBuilder(String chatId) {
        return new SendDice.Builder().chatId(chatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendDice";
    }

    @Override
    public @NotNull Dice deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Dice.class);
    }

}
