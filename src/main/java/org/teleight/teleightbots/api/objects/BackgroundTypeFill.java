package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.checkerframework.common.value.qual.IntRange;
import org.jetbrains.annotations.NotNull;

public record BackgroundTypeFill(
        @JsonProperty(value = "fill", required = true)
        @NotNull
        BackgroundFill fill,

        @JsonProperty(value = "dark_theme_dimming", required = true)
        @IntRange(from = 0, to = 100)
        int darkThemeDimming
) implements BackgroundType {

    @Override
    public String type() {
        return "fill";
    }

}
