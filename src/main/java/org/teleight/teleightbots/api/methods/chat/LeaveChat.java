package org.teleight.teleightbots.api.methods.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder
public record LeaveChat(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements ApiMethodBoolean {
    @Override
    public @NotNull String getEndpointURL() {
        return "leaveChat";
    }

    public static class LeaveChatBuilder {
        @Tolerate
        public LeaveChatBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
