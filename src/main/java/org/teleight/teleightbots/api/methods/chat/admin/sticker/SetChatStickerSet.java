package org.teleight.teleightbots.api.methods.chat.admin.sticker;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder
public record SetChatStickerSet(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "sticker_set_name", required = true)
        @NotNull
        String stickerSetName
) implements ApiMethodBoolean {

    @Override
    public @NotNull String getEndpointURL() {
        return "setChatStickerSet";
    }

    public static class SetChatStickerSetBuilder {
        @Tolerate
        public SetChatStickerSetBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
