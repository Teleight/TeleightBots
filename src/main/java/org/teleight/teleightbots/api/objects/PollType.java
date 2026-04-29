package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PollType {

    REGULAR("regular"),
    QUIZ("quiz");

    private final String fieldValue;

    PollType(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @JsonValue
    public String getFieldValue() {
        return fieldValue;
    }

    @JsonCreator
    public static PollType fromValue(String value) {
        for (PollType style : PollType.values()) {
            if (style.fieldValue.equalsIgnoreCase(value)) {
                return style;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + value);
    }

}
