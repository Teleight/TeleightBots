package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

public record StoryAreaTypeWeather(
        @JsonProperty(value = "temperature", required = true)
        float temperature,

        @JsonProperty(value = "emoji", required = true)
        @NotNull
        String emoji,

        @JsonProperty(value = "background_color", required = true)
        @NotNull
        Color backgroundColor
) implements StoryAreaType {

    @Override
    public String type() {
        return "weather";
    }
}
