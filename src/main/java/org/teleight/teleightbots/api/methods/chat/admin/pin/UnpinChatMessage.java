package org.teleight.teleightbots.api.methods.chat.admin.pin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder
public record UnpinChatMessage(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_id")
        @Nullable
        Integer description
) implements ApiMethodBoolean {
    @Override
    public @NotNull String getEndpointURL() {
        return "unpinChatMessage";
    }

    public static class UnpinChatMessageBuilder {
        @Tolerate
        public UnpinChatMessageBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
