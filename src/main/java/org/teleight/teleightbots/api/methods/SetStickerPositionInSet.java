package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetStickerPositionInSet(
        @JsonProperty(value = "sticker", required = true)
        @NotNull
        String sticker,

        @JsonProperty(value = "position", required = true)
        int position
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String sticker, int position) {
        return new SetStickerPositionInSet.Builder().sticker(sticker).position(position);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setStickerPositionInSet";
    }

}
