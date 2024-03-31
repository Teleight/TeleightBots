package org.teleight.teleightbots.api.methods.chat.admin.pin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder
public record PinChatMessage(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_id", required = true)
        @NotNull
        Integer description,

        @JsonProperty(value = "disable_notification")
        @Nullable
        Boolean disableNotification
) implements ApiMethodBoolean {
    @Override
    public @NotNull String getEndpointURL() {
        return "pinChatMessage";
    }

    public static class PinChatMessageBuilder {
        @Tolerate
        public PinChatMessageBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
