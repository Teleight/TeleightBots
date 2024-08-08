package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.PassportElementError;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetPassportDataErrors(
        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "errors", required = true)
        @NotNull
        PassportElementError[] errors
) implements ApiMethodBoolean {

    @Override
    public @NotNull String getEndpointURL() {
        return "setPassportDataErrors";
    }

}
