package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record DeleteMessage(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_id", required = true)
        int messageId
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String chatId, int messageId) {
        return new DeleteMessage.Builder().chatId(chatId).messageId(messageId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "deleteMessage";
    }

}
