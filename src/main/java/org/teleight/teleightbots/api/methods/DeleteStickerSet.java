package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record DeleteStickerSet(
        @JsonProperty(value = "name", required = true)
        @NotNull
        String name
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String name) {
        return new DeleteStickerSet.Builder().name(name);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "deleteStickerSet";
    }

}
