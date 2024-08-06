package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.ReactionType;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
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

    public static @NotNull Builder ofBuilder(String chatId, int messageId) {
        return new SetMessageReaction.Builder().chatId(chatId).messageId(messageId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setMessageReaction";
    }

}
