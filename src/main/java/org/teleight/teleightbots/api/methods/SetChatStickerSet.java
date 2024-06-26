package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record SetChatStickerSet(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "sticker_set_name", required = true)
        @NotNull
        String stickerSetName
) implements ApiMethodBoolean {

    public static Builder ofBuilder(String chatId, String stickerSetName) {
        return new SetChatStickerSet.Builder(chatId, stickerSetName);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setChatStickerSet";
    }

    public static class Builder {
        private final String chatId;
        private final String stickerSetName;

        Builder(String chatId, String stickerSetName) {
            this.chatId = chatId;
            this.stickerSetName = stickerSetName;
        }

        public SetChatStickerSet build() {
            return new SetChatStickerSet(this.chatId, this.stickerSetName);
        }
    }
}
