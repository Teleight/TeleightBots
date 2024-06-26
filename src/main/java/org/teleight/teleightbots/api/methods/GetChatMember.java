package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.ChatMember;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public record GetChatMember(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "user_id", required = true)
        long userId
) implements ApiMethod<ChatMember> {

    public static Builder ofBuilder(String chatId, long userId) {
        return new GetChatMember.Builder(chatId, userId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getChatMember";
    }

    @Override
    public @NotNull ChatMember deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, ChatMember.class);
    }

    public static class Builder {
        private final String chatId;
        private final Long userId;

        Builder(String chatId, Long userId) {
            this.chatId = chatId;
            this.userId = userId;
        }

        public GetChatMember build() {
            return new GetChatMember(this.chatId, this.userId);
        }
    }

}
