package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetBusinessAccountName(
        @JsonProperty(value = "business_connection_id", required = true)
        @NotNull
        String businessConnectionId,

        @JsonProperty(value = "first_name", required = true)
        @NotNull
        String firstName,

        @JsonProperty(value = "last_name")
        @Nullable
        String lastName
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String businessConnectionId, String firstName) {
        return new SetBusinessAccountName.Builder()
                .businessConnectionId(businessConnectionId)
                .firstName(firstName);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setBusinessAccountName";
    }
}
