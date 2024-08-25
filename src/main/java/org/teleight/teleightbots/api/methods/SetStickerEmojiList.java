package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetStickerEmojiList(
        @JsonProperty(value = "sticker", required = true)
        @NotNull
        String sticker,

        @JsonProperty(value = "emoji_list", required = true)
        @NotNull
        String[] emojiList
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String sticker, String[] emojiList) {
        return new SetStickerEmojiList.Builder().sticker(sticker).emojiList(emojiList);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setStickerEmojiList";
    }

}
