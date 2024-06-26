package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.serialization.SimpleFieldValueProvider;

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

    public enum Format implements SimpleFieldValueProvider {
        STATIC("static"),
        ANIMATED("animated"),
        VIDEO("video");

        private final String fieldValue;

        Format(String fieldValue) {
            this.fieldValue = fieldValue;
        }

        @Override
        public String getFieldValue() {
            return fieldValue;
        }
    }
}
