package org.teleight.teleightbots.api.methods.inline;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record AnswerCallbackQuery(
        @JsonProperty(value = "callback_query_id",required = true)
        String callbackQueryId,

        @JsonProperty("text")
        @Nullable
        String text,

        @JsonProperty("show_alert")
        boolean showAlert
) implements ApiMethodBoolean {

    public static @NotNull AnswerCallbackQuery of(String callbackQueryId) {
        return new AnswerCallbackQuery(callbackQueryId, null, false);
    }

    public @NotNull AnswerCallbackQuery withText(String text) {
        return new AnswerCallbackQuery(callbackQueryId, text, true);
    }

    public static @NotNull Builder builder() {
        return new BuilderImpl();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "answerCallbackQuery";
    }

    public sealed interface Builder permits BuilderImpl {
        @NotNull Builder callbackQueryId(@NotNull String callbackQueryId);

        @NotNull Builder text(String text);

        @NotNull Builder showAlert(boolean showAlert);

        @NotNull AnswerCallbackQuery build();
    }

    static final class BuilderImpl implements Builder {
        private String callbackQueryId;
        private String text;
        private boolean showAlert;

        @Override
        public @NotNull Builder callbackQueryId(@NotNull String callbackQueryId) {
            this.callbackQueryId = callbackQueryId;
            return this;
        }

        @Override
        public @NotNull Builder text(String text) {
            this.text = text;
            return this;
        }

        @Override
        public @NotNull Builder showAlert(boolean showAlert) {
            this.showAlert = showAlert;
            return this;
        }

        @Override
        public @NotNull AnswerCallbackQuery build() {
            return new AnswerCallbackQuery(callbackQueryId, text, showAlert);
        }
    }

}
