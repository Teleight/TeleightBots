package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record RevokeChatInviteLink(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "invite_link", required = true)
        @NotNull
        String inviteLink
) implements ApiMethodBoolean {

    public static Builder of(String chatId, String inviteLink) {
        return new Builder(chatId, inviteLink);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "revokeChatInviteLink";
    }

    public static class Builder {
        private final String chatId;
        private final String inviteLink;

        Builder(String chatId, String inviteLink) {
            this.chatId = chatId;
            this.inviteLink = inviteLink;
        }

        public RevokeChatInviteLink build() {
            return new RevokeChatInviteLink(this.chatId, this.inviteLink);
        }
    }
}
