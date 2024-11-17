package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.GameHighScore;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GetGameHighScores(
        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "chat_id")
        @Nullable
        String chatId,

        @JsonProperty(value = "message_id")
        int messageId,

        @JsonProperty(value = "inline_message_id")
        @Nullable
        String inlineMessageId
) implements ApiMethod<GameHighScore[]> {

    public static @NotNull Builder ofBuilder(long userId) {
        return new GetGameHighScores.Builder().userId(userId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getGameHighScores";
    }

    @Override
    public GameHighScore @NotNull [] deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, GameHighScore[].class);
    }

}
