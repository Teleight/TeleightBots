package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.ChatAction;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SendChatAction(
        @JsonProperty(value = "business_connection_id")
        @Nullable
        String businessConnectionId,

        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id")
        int messageThreadId,

        @JsonProperty(value = "action", required = true)
        @NotNull
        ChatAction action
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String chatId, ChatAction action) {
        return new SendChatAction.Builder().chatId(chatId).action(action);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendChatAction";
    }

}
