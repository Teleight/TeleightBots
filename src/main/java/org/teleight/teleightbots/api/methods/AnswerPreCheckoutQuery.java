package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record AnswerPreCheckoutQuery(
        @JsonProperty(value = "pre_checkout_query_id", required = true)
        @NotNull
        String preCheckoutQueryId,

        @JsonProperty(value = "ok", required = true)
        boolean ok,

        @JsonProperty(value = "error_message")
        @Nullable
        String errorMessage
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String preCheckoutQueryId, boolean ok) {
        return new AnswerPreCheckoutQuery.Builder().preCheckoutQueryId(preCheckoutQueryId).ok(ok);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "answerPreCheckoutQuery";
    }

}
