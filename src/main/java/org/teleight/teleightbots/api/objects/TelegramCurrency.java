package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TelegramCurrency {

    XTR("XTR"),
    TON("TON");

    private final String fieldValue;

    TelegramCurrency(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @JsonValue
    public String getFieldValue() {
        return fieldValue;
    }

    @JsonCreator
    public static TelegramCurrency fromValue(String value) {
        for (TelegramCurrency currency : TelegramCurrency.values()) {
            if (currency.fieldValue.equalsIgnoreCase(value)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + value);
    }

}
