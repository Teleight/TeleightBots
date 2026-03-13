package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UniqueGiftOrigin {

    UPGRADE("upgrade"),
    TRANSFER("transfer"),
    RESALE("resale"),
    GIFTED_UPGRADE("gifted_upgrade"),
    OFFER("offer");

    private final String fieldValue;

    UniqueGiftOrigin(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @JsonValue
    public String getFieldValue() {
        return fieldValue;
    }

    @JsonCreator
    public static UniqueGiftOrigin fromValue(String value) {
        for (UniqueGiftOrigin giftOrigin : UniqueGiftOrigin.values()) {
            if (giftOrigin.fieldValue.equalsIgnoreCase(value)) {
                return giftOrigin;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + value);
    }
}
