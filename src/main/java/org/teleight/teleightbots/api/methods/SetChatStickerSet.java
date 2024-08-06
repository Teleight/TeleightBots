package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetChatStickerSet(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "sticker_set_name", required = true)
        @NotNull
        String stickerSetName
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String chatId, String stickerSetName) {
        return new SetChatStickerSet.Builder().chatId(chatId).stickerSetName(stickerSetName);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setChatStickerSet";
    }

}
