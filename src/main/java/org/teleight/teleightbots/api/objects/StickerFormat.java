package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StickerFormat {

    STATIC("static"),
    ANIMATED("animated"),
    VIDEO("video");

    private final String fieldValue;

    StickerFormat(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @JsonValue
    public String getFieldValue() {
        return fieldValue;
    }

    @JsonCreator
    public static StickerFormat fromValue(String value) {
        for (StickerFormat stickerFormat : StickerFormat.values()) {
            if (stickerFormat.fieldValue.equalsIgnoreCase(value)) {
                return stickerFormat;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + value);
    }

}
