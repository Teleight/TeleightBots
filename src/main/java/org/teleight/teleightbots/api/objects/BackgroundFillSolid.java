package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

public record BackgroundFillSolid(
        @JsonProperty(value = "color", required = true)
        @NotNull
        Color color
) implements BackgroundFill {

    @Override
    public String type() {
        return "solid";
    }

}
