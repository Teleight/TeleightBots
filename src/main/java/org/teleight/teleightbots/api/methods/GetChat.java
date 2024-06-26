package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.ChatFullInfo;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public record GetChat(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements ApiMethod<ChatFullInfo> {

    public static Builder ofBuilder(String chatId) {
        return new GetChat.Builder(chatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getChat";
    }

    @Override
    public @NotNull ChatFullInfo deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, ChatFullInfo.class);
    }

    public static class Builder {
        private final String chatId;

        Builder(String chatId) {
            this.chatId = chatId;
        }

        public GetChat build() {
            return new GetChat(this.chatId);
        }
    }
}
