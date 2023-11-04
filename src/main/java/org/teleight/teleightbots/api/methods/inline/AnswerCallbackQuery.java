package org.teleight.teleightbots.api.methods.inline;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record AnswerCallbackQuery(
        @JsonProperty("callback_query_id")
        @NotNull
        String callbackQueryId
) implements ApiMethodBoolean {

    public static @NotNull AnswerCallbackQuery of(String callbackQueryId) {
        return new AnswerCallbackQuery(callbackQueryId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "answerCallbackQuery";
    }

}
