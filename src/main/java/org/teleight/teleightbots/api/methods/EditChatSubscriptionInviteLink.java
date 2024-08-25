package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.ChatInviteLink;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record EditChatSubscriptionInviteLink(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "invite_link", required = true)
        @NotNull
        String inviteLink,

        @JsonProperty("name")
        @Nullable
        String name
) implements ApiMethod<ChatInviteLink> {

    public static @NotNull Builder ofBuilder(String chatId, String inviteLink) {
        return new EditChatSubscriptionInviteLink.Builder().chatId(chatId).inviteLink(inviteLink);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "editChatSubscriptionInviteLink";
    }

    @Override
    public @NotNull ChatInviteLink deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, ChatInviteLink.class);
    }

}
