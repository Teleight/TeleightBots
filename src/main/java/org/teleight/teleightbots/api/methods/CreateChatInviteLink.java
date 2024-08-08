package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.ChatInviteLink;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

import java.util.Date;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record CreateChatInviteLink(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

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
) implements ApiMethod<ChatInviteLink> {

    public static @NotNull Builder ofBuilder(String chatId) {
        return new CreateChatInviteLink.Builder().chatId(chatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "createChatInviteLink";
    }

    @Override
    public @NotNull ChatInviteLink deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, ChatInviteLink.class);
    }

}
