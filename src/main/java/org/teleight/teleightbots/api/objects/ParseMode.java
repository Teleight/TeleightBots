package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ParseMode {

    MARKDOWN("Markdown"),
    MARKDOWNV2("MarkdownV2"),
    HTML("html");

    private final String fieldValue;

    ParseMode(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @JsonValue
    public String getFieldValue() {
        return fieldValue;
    }

    @JsonCreator
    public static ParseMode fromValue(String value) {
        for (ParseMode parseMode : ParseMode.values()) {
            if (parseMode.fieldValue.equalsIgnoreCase(value)) {
                return parseMode;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + value);
    }

}
