package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum KeyboardButtonStyle {

    DANGER("danger"),
    SUCCESS("success"),
    PRIMARY("primary");

    private final String fieldValue;

    KeyboardButtonStyle(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @JsonValue
    public String getFieldValue() {
        return fieldValue;
    }

    @JsonCreator
    public static KeyboardButtonStyle fromValue(String value) {
        for (KeyboardButtonStyle style : KeyboardButtonStyle.values()) {
            if (style.fieldValue.equalsIgnoreCase(value)) {
                return style;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + value);
    }

}
