package org.teleight.teleightbots.api.methods.group.admin.sticker;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

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
