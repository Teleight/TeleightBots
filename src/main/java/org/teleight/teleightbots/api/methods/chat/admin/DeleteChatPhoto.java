package org.teleight.teleightbots.api.methods.chat.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder
public record DeleteChatPhoto(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements ApiMethodBoolean {
    @Override
    public @NotNull String getEndpointURL() {
        return "deleteChatPhoto";
    }

    public static class DeleteChatPhotoBuilder {
        @Tolerate
        public DeleteChatPhotoBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
