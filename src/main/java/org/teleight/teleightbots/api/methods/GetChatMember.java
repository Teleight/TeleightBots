package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.ChatMember;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GetChatMember(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "user_id", required = true)
        long userId
) implements ApiMethod<ChatMember> {

    public static @NotNull Builder ofBuilder(String chatId, long userId) {
        return new GetChatMember.Builder().chatId(chatId).userId(userId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getChatMember";
    }

    @Override
    public @NotNull ChatMember deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, ChatMember.class);
    }

}
