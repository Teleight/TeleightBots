package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.ReactionType;

public record SetMessageReaction(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_id", required = true)
        int messageId,

        @JsonProperty(value = "reaction")
        @Nullable
        ReactionType[] reaction,

        @JsonProperty(value = "is_big")
        boolean isBig
) implements ApiMethodBoolean {

    public static Builder of(String chatId, int messageId) {
        return new SetMessageReaction.Builder(chatId, messageId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setMessageReaction";
    }

    public static class Builder {
        private final String chatId;
        private final Integer messageId;
        private ReactionType[] reaction;
        private boolean isBig;

        Builder(String chatId, Integer messageId) {
            this.chatId = chatId;
            this.messageId = messageId;
        }

        public Builder reaction(ReactionType[] reaction) {
            this.reaction = reaction;
            return this;
        }

        public Builder isBig(boolean isBig) {
            this.isBig = isBig;
            return this;
        }

        public SetMessageReaction build() {
            return new SetMessageReaction(this.chatId, this.messageId, this.reaction, this.isBig);
        }
    }
}
