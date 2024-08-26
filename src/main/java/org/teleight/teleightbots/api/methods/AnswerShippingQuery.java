package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.ShippingOption;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record AnswerShippingQuery(
        @JsonProperty(value = "shipping_query_id", required = true)
        @NotNull
        String shippingQueryId,

        @JsonProperty(value = "ok", required = true)
        boolean ok,

        @JsonProperty("shipping_options")
        @Nullable
        ShippingOption[] shippingOptions,

        @JsonProperty("error_message")
        @Nullable
        String errorMessage
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String shippingQueryId, boolean ok) {
        return new AnswerShippingQuery.Builder().shippingQueryId(shippingQueryId).ok(ok);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "answerShippingQuery";
    }

}
