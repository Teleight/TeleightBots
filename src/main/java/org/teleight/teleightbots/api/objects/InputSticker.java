package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record InputSticker(
        @JsonProperty(value = "sticker", required = true)
        @NotNull
        InputFile sticker,

        @JsonProperty(value = "format", required = true)
        @NotNull
        StickerFormat format,

        @JsonProperty(value = "emoji_list")
        @Nullable
        String[] emojiList,

        @JsonProperty(value = "mask_position")
        @Nullable
        MaskPosition maskPosition,

        @JsonProperty(value = "keywords")
        @Nullable
        String[] keywords
) implements ApiResult {

    public static @NotNull Builder ofBuilder(InputFile sticker, StickerFormat format) {
        return new InputSticker.Builder().sticker(sticker).format(format);
    }

}
