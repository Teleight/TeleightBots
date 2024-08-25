package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetStickerKeywords(
        @JsonProperty(value = "sticker", required = true)
        @NotNull
        String sticker,

        @JsonProperty(value = "keywords", required = true)
        @NotNull
        String[] keywords
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String sticker, String[] keywords) {
        return new SetStickerKeywords.Builder().sticker(sticker).keywords(keywords);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setStickerKeywords";
    }

}
