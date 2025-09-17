package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SuggestedPostRefundReason {
    POST_DELETED("post_deleted"),
    PAYMENT_REFUNDED("payment_refunded");

    private final String fieldValue;

    SuggestedPostRefundReason(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @JsonValue
    public String getFieldValue() {
        return fieldValue;
    }

    @JsonCreator
    public static SuggestedPostRefundReason fromValue(String value) {
        for (SuggestedPostRefundReason state : SuggestedPostRefundReason.values()) {
            if (state.fieldValue.equalsIgnoreCase(value)) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + value);
    }

}
