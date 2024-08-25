package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.MaskPosition;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetStickerMaskPosition(
        @JsonProperty(value = "sticker", required = true)
        @NotNull
        String sticker,

        @JsonProperty("mask_position")
        @Nullable
        MaskPosition maskPosition
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String sticker) {
        return new SetStickerMaskPosition.Builder().sticker(sticker);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setStickerMaskPosition";
    }

}
