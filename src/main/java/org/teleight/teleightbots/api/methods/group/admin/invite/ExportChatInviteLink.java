package org.teleight.teleightbots.api.methods.group.admin.invite;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder
public record ExportChatInviteLink(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements ApiMethodBoolean {
    @Override
    public @NotNull String getEndpointURL() {
        return "exportChatInviteLink";
    }

    public static class ExportChatInviteLinkBuilder {
        @Tolerate
        public ExportChatInviteLinkBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
