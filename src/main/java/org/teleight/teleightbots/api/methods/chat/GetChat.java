package org.teleight.teleightbots.api.methods.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.chat.Chat;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder
public record GetChat(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements ApiMethod<Chat> {
    @Override
    public @NotNull Chat deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Chat.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getChat";
    }

    public static class GetChatBuilder {
        @Tolerate
        public GetChatBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
