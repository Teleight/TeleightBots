package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record AnswerCallbackQuery(
        @JsonProperty(value = "callback_query_id", required = true)
        @NotNull
        String callbackQueryId,

        @JsonProperty("text")
        @Nullable
        String text,

        @JsonProperty("show_alert")
        boolean showAlert
) implements ApiMethodBoolean {

    public static Builder of(String callbackQueryId) {
        return new AnswerCallbackQuery.Builder(callbackQueryId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "answerCallbackQuery";
    }

    public static final class Builder {
        private final String callbackQueryId;
        private String text;
        private boolean showAlert;

        public Builder(String callbackQueryId) {
            this.callbackQueryId = callbackQueryId;
        }

        public @NotNull Builder text(String text) {
            this.text = text;
            return this;
        }

        public @NotNull Builder showAlert(boolean showAlert) {
            this.showAlert = showAlert;
            return this;
        }

        public AnswerCallbackQuery build() {
            return new AnswerCallbackQuery(this.callbackQueryId, this.text, this.showAlert);
        }
    }

}
