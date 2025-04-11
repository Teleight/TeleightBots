package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetBusinessAccountBio(
        @JsonProperty(value = "business_connection_id", required = true)
        @NotNull
        String businessConnectionId,

        @JsonProperty(value = "bio")
        @Nullable
        String bio
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String businessConnectionId) {
        return new SetBusinessAccountBio.Builder()
                .businessConnectionId(businessConnectionId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setBusinessAccountBio";
    }
}
