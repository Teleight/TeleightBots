package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StoryActivePeriod {

    SIX_HOURS(6 * 3600),
    TWELVE_HOURS(12 * 3600),
    A_DAY(86400),
    TWO_DAYS(3 * 86400);

    private final int fieldValue;

    StoryActivePeriod(int fieldValue) {
        this.fieldValue = fieldValue;
    }

    @JsonValue
    public int getFieldValue() {
        return fieldValue;
    }

    @JsonCreator
    public static StoryActivePeriod fromValue(int value) {
        for (StoryActivePeriod period : StoryActivePeriod.values()) {
            if (period.fieldValue == value) {
                return period;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + value);
    }


}
