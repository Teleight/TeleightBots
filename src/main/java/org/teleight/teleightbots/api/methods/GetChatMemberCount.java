package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;

public record GetChatMemberCount(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements ApiMethod<Integer> {

    public static Builder of(String chatId) {
        return new GetChatMemberCount.Builder(chatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getChatMemberCount";
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
