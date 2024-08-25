package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record DeleteStickerFromSet(
        @JsonProperty(value = "sticker", required = true)
        @NotNull
        String sticker
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String sticker) {
        return new DeleteStickerFromSet.Builder().sticker(sticker);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "deleteStickerFromSet";
    }

}
