package org.teleight.teleightbots.api.methods.updating;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder
public record DeleteMessage(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_id", required = true)
        @NotNull
        Integer messageId
) implements ApiMethodBoolean {

    @Override
    public @NotNull String getEndpointURL() {
        return "deleteMessage";
    }

    public static class DeleteMessageBuilder {
        @Tolerate
        public DeleteMessageBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }

}
