package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.checkerframework.common.value.qual.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodMultiResponse;
import org.teleight.teleightbots.api.objects.Message;

import java.io.Serializable;
import java.util.List;

public record SetGameScore(
        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "score", required = true)
        @IntRange(from = 0)
        int score,

        @JsonProperty("force")
        boolean force,

        @JsonProperty("disable_edit_message")
        boolean disableEditMessage,

        @JsonProperty("chat_id")
        @Nullable
        String chatId,

        @JsonProperty("message_id")
        int messageId,

        @JsonProperty("inline_message_id")
        @Nullable
        String inlineMessageId
) implements ApiMethodMultiResponse {

    public static SetGameScore.Builder ofBuilder(long userId, @IntRange(from = 0) int score) {
        return new SetGameScore.Builder(userId, score);
    }

    @Override
    public List<Class<? extends Serializable>> getSerializableClasses() {
        return List.of(Message.class, Boolean.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setGameScore";
    }

    public static class Builder {
        private final long userId;
        private int score;
        private boolean force;
        private boolean disableEditMessage;
        private String chatId;
        private int messageId;
        private String inlineMessageId;

        Builder(long userId, @IntRange(from = 0) int score) {
            this.userId = userId;
            this.score = score;
        }

        public Builder score(int score) {
            this.score = score;
            return this;
        }

        public Builder force(boolean force) {
            this.force = force;
            return this;
        }

        public Builder disableEditMessage(boolean disableEditMessage) {
            this.disableEditMessage = disableEditMessage;
            return this;
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

        public SetGameScore build() {
            return new SetGameScore(this.userId, this.score, this.force, this.disableEditMessage, this.chatId, this.messageId, this.inlineMessageId);
        }
    }

}
