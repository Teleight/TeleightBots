package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetMyName(
        @JsonProperty(value = "name")
        @Nullable
        String name,

        @JsonProperty(value = "language_code")
        @Nullable
        String languageCode
) implements ApiMethodBoolean {

    @Override
    public @NotNull String getEndpointURL() {
        return "setMyName";
    }

}
