package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

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

        private final String fieldValue;

        Format(String fieldValue) {
            this.fieldValue = fieldValue;
        }

        @JsonValue
        public String getFieldValue() {
            return fieldValue;
        }

        @JsonCreator
        public static InputSticker.Format fromValue(String value) {
            for (InputSticker.Format format : InputSticker.Format.values()) {
                if (format.fieldValue.equalsIgnoreCase(value)) {
                    return format;
                }
            }
            throw new IllegalArgumentException("Unknown enum type " + value);
        }
    }
}
