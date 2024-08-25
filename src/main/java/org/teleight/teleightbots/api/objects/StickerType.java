package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StickerType {

    REGULAR("regular"),
    MASK("mask"),
    CUSTOM_EMOJI("custom_emoji");

    private final String fieldValue;

    StickerType(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @JsonValue
    public String getFieldValue() {
        return fieldValue;
    }

    @JsonCreator
    public static StickerType fromValue(String value) {
        for (StickerType stickerFormat : StickerType.values()) {
            if (stickerFormat.fieldValue.equalsIgnoreCase(value)) {
                return stickerFormat;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + value);
    }

}
