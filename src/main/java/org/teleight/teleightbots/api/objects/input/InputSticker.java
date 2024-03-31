package org.teleight.teleightbots.api.objects.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.MaskPosition;

public record InputSticker(
        @JsonProperty(value = "sticker", required = true)
        @NotNull
        InputFile sticker,

        @JsonProperty(value = "format", required = true)
        @NotNull
        Format format,

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

    public enum Format {
        STATIC("static"),
        ANIMATED("animated"),
        VIDEO("video");

        private final String value;

        Format(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
