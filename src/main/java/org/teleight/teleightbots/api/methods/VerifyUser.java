package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record VerifyUser(
        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "custom_description")
        @Nullable
        String customDescription // 0-70 characters
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(long userId) {
        return new VerifyUser.Builder().userId(userId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "verifyUser";
    }

}
