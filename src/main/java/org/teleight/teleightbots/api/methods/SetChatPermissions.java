package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.ChatPermissions;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetChatPermissions(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "permissions", required = true)
        @NotNull
        ChatPermissions senderChatId,

        @JsonProperty(value = "use_independent_chat_permissions")
        boolean useIndependentChatPermissions
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String chatId, ChatPermissions senderChatId) {
        return new SetChatPermissions.Builder().chatId(chatId).senderChatId(senderChatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setChatPermissions";
    }

}
