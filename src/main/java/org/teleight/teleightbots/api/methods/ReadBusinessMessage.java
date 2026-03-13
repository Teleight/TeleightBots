package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record ReadBusinessMessage(
        @JsonProperty(value = "business_connection_id", required = true)
        @NotNull
        String businessConnectionId,

        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_id", required = true)
        int messageId
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String businessConnectionId, String chatId, int messageId) {
        return new ReadBusinessMessage.Builder()
                .businessConnectionId(businessConnectionId)
                .chatId(chatId)
                .messageId(messageId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "readBusinessMessage";
    }
}
