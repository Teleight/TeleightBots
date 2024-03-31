package org.teleight.teleightbots.api.methods.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder
public record GetChatMemberCount(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements ApiMethod<Integer> {

    @Override
    public @NotNull Integer deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Integer.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getChatMemberCount";
    }

    public static class GetChatMemberCountBuilder {
        @Tolerate
        public GetChatMemberCountBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
