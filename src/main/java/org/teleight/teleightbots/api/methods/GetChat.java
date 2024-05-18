package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.Chat;

public record GetChat(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements ApiMethod<Chat> {

    public static Builder ofBuilder(String chatId) {
        return new GetChat.Builder(chatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getChat";
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
