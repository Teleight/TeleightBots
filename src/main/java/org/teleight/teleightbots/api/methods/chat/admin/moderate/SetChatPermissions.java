package org.teleight.teleightbots.api.methods.chat.admin.moderate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.chat.ChatPermissions;

@Builder
public record SetChatPermissions(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "permissions", required = true)
        @NotNull
        ChatPermissions senderChatId,

        @JsonProperty(value = "use_independent_chat_permissions")
        @Nullable
        Boolean useIndependentChatPermissions
) implements ApiMethodBoolean {
    @Override
    public @NotNull String getEndpointURL() {
        return "setChatPermissions";
    }

    public static class SetChatPermissionsBuilder {
        @Tolerate
        public SetChatPermissionsBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
