package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record ExportChatInviteLink(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements ApiMethodBoolean {

    public static Builder ofBuilder(String chat) {
        return new ExportChatInviteLink.Builder(chat);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "exportChatInviteLink";
    }

    public static class Builder {
        private final String chatId;

        Builder(String chatId) {
            this.chatId = chatId;
        }

        public ExportChatInviteLink build() {
            return new ExportChatInviteLink(this.chatId);
        }
    }
}
