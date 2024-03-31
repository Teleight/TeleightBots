package org.teleight.teleightbots.api.methods.chat.admin.invite;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder
public record RevokeChatInviteLink(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "invite_link", required = true)
        @NotNull
        String inviteLink
) implements ApiMethodBoolean {
    @Override
    public @NotNull String getEndpointURL() {
        return "revokeChatInviteLink";
    }

    public static class RevokeChatInviteLinkBuilder {
        @Tolerate
        public RevokeChatInviteLinkBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
