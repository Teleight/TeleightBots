package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.checkerframework.common.value.qual.IntRange;
import org.jetbrains.annotations.NotNull;

public record BackgroundTypeWallpaper(
        @JsonProperty(value = "document", required = true)
        @NotNull
        Document document,

        @JsonProperty(value = "dark_theme_dimming", required = true)
        @IntRange(from = 0, to = 100)
        int darkThemeDimming,

        @JsonProperty(value = "is_blurred")
        boolean isBlurred,

        @JsonProperty(value = "is_moving")
        boolean isMoving
) implements BackgroundType {

    @Override
    public String type() {
        return "wallpaper";
    }

}
