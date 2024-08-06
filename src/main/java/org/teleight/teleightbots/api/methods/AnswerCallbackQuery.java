package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
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

    public static @NotNull Builder ofBuilder(String callbackQueryId) {
        return new AnswerCallbackQuery.Builder().callbackQueryId(callbackQueryId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "answerCallbackQuery";
    }

}
