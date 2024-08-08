package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.Poll;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record StopPoll(
        @JsonProperty(value = "business_connection_id")
        @Nullable
        String businessConnectionId,

        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_id", required = true)
        int messageId,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements ApiMethod<Poll> {

    public static @NotNull Builder ofBuilder(@NotNull String chatId, int messageId) {
        return new StopPoll.Builder().chatId(chatId).messageId(messageId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "stopPoll";
    }

    @Override
    public @NotNull Poll deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Poll.class);
    }

}
