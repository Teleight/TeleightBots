package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public record GetChatMemberCount(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements ApiMethod<Integer> {

    public static Builder ofBuilder(String chatId) {
        return new GetChatMemberCount.Builder(chatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getChatMemberCount";
    }

    @Override
    public @NotNull Integer deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Integer.class);
    }

    public static class Builder {
        private final String chatId;

        Builder(String chatId) {
            this.chatId = chatId;
        }

        public GetChatMemberCount build() {
            return new GetChatMemberCount(this.chatId);
        }
    }
}
