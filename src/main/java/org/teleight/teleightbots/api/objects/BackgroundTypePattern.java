package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.checkerframework.common.value.qual.IntRange;
import org.jetbrains.annotations.NotNull;

public record BackgroundTypePattern(
        @JsonProperty(value = "document", required = true)
        @NotNull
        Document document,

        @JsonProperty(value = "fill", required = true)
        @NotNull
        BackgroundFill fill,

        @JsonProperty(value = "intensity", required = true)
        @IntRange(from = 0, to = 100)
        int intensity,

        @JsonProperty(value = "is_inverted")
        boolean isInverted,

        @JsonProperty(value = "is_moving")
        boolean isMoving
) implements BackgroundType {

    @Override
    public String type() {
        return "pattern";
    }

}
