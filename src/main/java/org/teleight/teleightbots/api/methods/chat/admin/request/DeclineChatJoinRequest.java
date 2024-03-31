package org.teleight.teleightbots.api.methods.chat.admin.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder
public record DeclineChatJoinRequest(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "user_id", required = true)
        @NotNull
        Long userId
) implements ApiMethodBoolean {
    @Override
    public @NotNull String getEndpointURL() {
        return "declineChatJoinRequest";
    }

    public static class DeclineChatJoinRequestBuilder {
        @Tolerate
        public DeclineChatJoinRequestBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
