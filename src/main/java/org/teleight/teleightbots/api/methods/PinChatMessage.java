package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record PinChatMessage(
        @JsonProperty(value = "business_connection_id")
        @Nullable
        String businessConnectionId,

        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_id", required = true)
        int messageId,

        @JsonProperty(value = "disable_notification")
        boolean disableNotification
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String chatId, int messageId) {
        return new PinChatMessage.Builder().chatId(chatId).messageId(messageId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "pinChatMessage";
    }

}
