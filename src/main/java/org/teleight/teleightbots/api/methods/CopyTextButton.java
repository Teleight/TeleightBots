package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record CopyTextButton(
        @JsonProperty(value = "text", required = true)
        @NotNull
        String text
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String text) {
        return new CopyTextButton.Builder().text(text);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "copyTextButton";
    }

}
