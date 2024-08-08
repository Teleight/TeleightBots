package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

import java.util.Date;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record EditChatInviteLink(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "invite_link", required = true)
        @NotNull
        String inviteLink,

        @JsonProperty(value = "name")
        @Nullable
        String name,

        @JsonProperty(value = "expire_date")
        @Nullable
        Date expireDate,

        @JsonProperty(value = "member_limit")
        int memberLimit,

        @JsonProperty(value = "creates_join_request")
        boolean createsJoinRequest
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String chatId, String inviteLink) {
        return new EditChatInviteLink.Builder().chatId(chatId).inviteLink(inviteLink);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "editChatInviteLink";
    }

}
