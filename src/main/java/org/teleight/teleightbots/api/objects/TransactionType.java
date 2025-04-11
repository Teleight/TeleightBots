package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionType {

    INVOICE_PAYMENT("invoice_payment"),
    PAID_MEDIA_PAYMENT("paid_media_payment"),
    GIFT_PURCHASE("gift_purchase"),
    PREMIUM_PURCHASE("premium_purchase"),
    BUSINESS_ACCOUNT_TRANSFER("business_account_transfer");

    private final String fieldValue;

    TransactionType(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @JsonValue
    public String getFieldValue() {
        return fieldValue;
    }

    @JsonCreator
    public static TransactionType fromValue(String value) {
        for (TransactionType transactionType : TransactionType.values()) {
            if (transactionType.fieldValue.equalsIgnoreCase(value)) {
                return transactionType;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + value);
    }
}
