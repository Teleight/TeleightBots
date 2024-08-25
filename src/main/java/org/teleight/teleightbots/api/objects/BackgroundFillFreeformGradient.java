package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

public record BackgroundFillFreeformGradient(
        @JsonProperty(value = "colors", required = true)
        @NotNull
        Color[] colors
) implements BackgroundFill {

    @Override
    public String type() {
        return "freeform_gradient";
    }

}
