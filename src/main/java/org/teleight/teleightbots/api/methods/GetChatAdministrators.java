package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.ChatMember;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public record GetChatAdministrators(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements ApiMethod<ChatMember[]> {

    public static Builder ofBuilder(String chatId) {
        return new GetChatAdministrators.Builder(chatId);
    }

    @Override
    public ChatMember @NotNull [] deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponseArray(answer, ChatMember[].class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getChatAdministrators";
    }

    public static class Builder {
        private final String chatId;

        Builder(String chatId) {
            this.chatId = chatId;
        }

        public GetChatAdministrators build() {
            return new GetChatAdministrators(this.chatId);
        }
    }
}
