package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record RevokeChatInviteLink(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "invite_link", required = true)
        @NotNull
        String inviteLink
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String chatId, String inviteLink) {
        return new RevokeChatInviteLink.Builder().chatId(chatId).inviteLink(inviteLink);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "revokeChatInviteLink";
    }

}
