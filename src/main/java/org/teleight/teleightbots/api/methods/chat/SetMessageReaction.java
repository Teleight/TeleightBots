package org.teleight.teleightbots.api.methods.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.reaction.ReactionType;

@Builder
public record SetMessageReaction(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_id", required = true)
        @NotNull
        Integer messageId,

        @JsonProperty(value = "reaction")
        @Nullable
        ReactionType[] reaction,

        @JsonProperty(value = "is_big")
        @Nullable
        Boolean isBig
) implements ApiMethodBoolean {

    @Override
    public @NotNull String getEndpointURL() {
        return "setMessageReaction";
    }

    public static class SetMessageReactionBuilder {
        @Tolerate
        public SetMessageReactionBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
