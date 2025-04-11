package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.StarAmount;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GetBusinessAccountStarBalance(
        @JsonProperty(value = "business_connection_id", required = true)
        @NotNull
        String businessConnectionId
) implements ApiMethod<StarAmount> {

    public static @NotNull Builder ofBuilder(String businessConnectionId) {
        return new GetBusinessAccountStarBalance.Builder()
                .businessConnectionId(businessConnectionId);
    }

    @Override
    public @NotNull StarAmount deserializeResponse(@NotNull String answer) {
        return deserializeResponse(answer, StarAmount.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getBusinessAccountStarBalance";
    }
}
