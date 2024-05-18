package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.UserChatBoosts;

public record GetUserChatBoosts(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "user_id", required = true)
        long userId
) implements ApiMethod<UserChatBoosts> {

    public static Builder ofBuilder(String chatId, long userId) {
        return new GetUserChatBoosts.Builder(chatId, userId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getUserChatBoosts";
    }

    public static class Builder {
        private final String chatId;
        private final Long userId;

        Builder(String chatId, Long userId) {
            this.chatId = chatId;
            this.userId = userId;
        }

        public GetUserChatBoosts build() {
            return new GetUserChatBoosts(this.chatId, this.userId);
        }
    }
}
