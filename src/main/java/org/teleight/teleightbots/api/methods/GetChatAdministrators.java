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
public record GetChatAdministrators(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements ApiMethod<ChatMember[]> {

    public static @NotNull Builder ofBuilder(String chatId) {
        return new GetChatAdministrators.Builder().chatId(chatId);
    }

    @Override
    public ChatMember @NotNull [] deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponseArray(answer, ChatMember[].class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getChatAdministrators";
    }

}
