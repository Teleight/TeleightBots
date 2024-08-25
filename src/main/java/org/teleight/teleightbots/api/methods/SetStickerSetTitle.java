package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetStickerSetTitle(
        @JsonProperty(value = "name", required = true)
        @NotNull
        String name,

        @JsonProperty(value = "title", required = true)
        @NotNull
        String title
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String name, String title) {
        return new SetStickerSetTitle.Builder().name(name).title(title);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setStickerSetTitle";
    }

}
