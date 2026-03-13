package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SuggestedPostState {

    PENDING("pending"),
    APPROVED("approved"),
    DECLINED("declined");

    private final String fieldValue;

    SuggestedPostState(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @JsonValue
    public String getFieldValue() {
        return fieldValue;
    }

    @JsonCreator
    public static SuggestedPostState fromValue(String value) {
        for (SuggestedPostState state : SuggestedPostState.values()) {
            if (state.fieldValue.equalsIgnoreCase(value)) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + value);
    }

}
