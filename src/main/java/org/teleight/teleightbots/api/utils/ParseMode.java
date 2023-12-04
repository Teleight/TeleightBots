package org.teleight.teleightbots.api.utils;

public enum ParseMode {

    MARKDOWN("Markdown"),
    MARKDOWNV2("MarkdownV2"),
    HTML("html");

    private final String fieldValue;

    ParseMode(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getFieldValue() {
        return fieldValue;
    }

}
