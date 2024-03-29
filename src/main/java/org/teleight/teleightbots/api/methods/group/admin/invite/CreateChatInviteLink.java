package org.teleight.teleightbots.api.methods.group.admin.invite;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder
public record CreateChatInviteLink(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "name")
        @Nullable
        String name,

        @JsonProperty(value = "expire_date")
        @Nullable
        Integer expireDate,

        @JsonProperty(value = "member_limit")
        @Nullable
        Integer memberLimit,

        @JsonProperty(value = "creates_join_request")
        @Nullable
        Boolean createsJoinRequest
) implements ApiMethodBoolean {
    @Override
    public @NotNull String getEndpointURL() {
        return "createChatInviteLink";
    }

    public static class CreateChatInviteLinkBuilder {
        @Tolerate
        public CreateChatInviteLinkBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
