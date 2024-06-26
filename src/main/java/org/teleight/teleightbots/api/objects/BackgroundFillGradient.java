package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.checkerframework.common.value.qual.IntRange;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

public record BackgroundFillGradient(
        @JsonProperty(value = "top_color", required = true)
        @NotNull
        Color topColor,

        @JsonProperty(value = "bottom_color", required = true)
        @NotNull
        Color bottomColor,

        @JsonProperty(value = "rotation_angle", required = true)
        @IntRange(from = 0, to = 359)
        int rotationAngle
) implements BackgroundFill {

    @Override
    public BackgroundFillType type() {
        return BackgroundFillType.GRADIENT;
    }

}
