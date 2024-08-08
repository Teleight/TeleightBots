package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record BatchDeleteMessages(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_ids", required = true)
        long[] messageIds
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String chatId, long[] messageIds) {
        return new BatchDeleteMessages.Builder().chatId(chatId).messageIds(messageIds);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "deleteMessages";
    }

}
