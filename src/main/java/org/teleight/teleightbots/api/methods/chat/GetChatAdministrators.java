package org.teleight.teleightbots.api.methods.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.chat.member.ChatMember;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder
public record GetChatAdministrators(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements ApiMethod<ChatMember[]> {

    @Override
    public ChatMember @NotNull [] deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, ChatMember[].class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getChatAdministrators";
    }

    public static class GetChatAdministratorsBuilder {
        @Tolerate
        public GetChatAdministratorsBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
