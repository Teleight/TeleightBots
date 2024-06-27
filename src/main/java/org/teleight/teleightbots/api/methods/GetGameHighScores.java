package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.GameHighScore;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public record GetGameHighScores(
        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty("chat_id")
        @Nullable
        String chatId,

        @JsonProperty("message_id")
        int messageId,

        @JsonProperty("inline_message_id")
        @Nullable
        String inlineMessageId
) implements ApiMethod<GameHighScore[]> {

    public static GetGameHighScores.Builder ofBuilder(long userId) {
        return new GetGameHighScores.Builder(userId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getGameHighScores";
    }

    @Override
    public GameHighScore @NotNull [] deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, GameHighScore[].class);
    }

    public static class Builder {
        private final long userId;
        private String chatId;
        private int messageId;
        private String inlineMessageId;

        Builder(long userId) {
            this.userId = userId;
        }

        public Builder chatId(String chatId) {
            this.chatId = chatId;
            return this;
        }

        public Builder messageId(int messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder inlineMessageId(String inlineMessageId) {
            this.inlineMessageId = inlineMessageId;
            return this;
        }

        public GetGameHighScores build() {
            return new GetGameHighScores(this.userId, this.chatId, this.messageId, this.inlineMessageId);
        }
    }

}
