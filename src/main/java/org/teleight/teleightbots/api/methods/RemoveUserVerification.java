package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record RemoveUserVerification(
        @JsonProperty(value = "user_id", required = true)
        long userId
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(long userId) {
        return new RemoveUserVerification.Builder().userId(userId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "removeUserVerification";
    }

}
