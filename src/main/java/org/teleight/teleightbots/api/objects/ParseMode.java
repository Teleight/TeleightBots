package org.teleight.teleightbots.api.objects;

import org.teleight.teleightbots.api.ApiMethod;

public enum ParseMode implements ApiMethod.SimpleFieldValueProvider {

    MARKDOWN("Markdown"),
    MARKDOWNV2("MarkdownV2"),
    HTML("html");

    private final String fieldValue;

    ParseMode(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @Override
    public String getFieldValue() {
        return fieldValue;
    }

}
