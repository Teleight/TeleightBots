package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

import java.awt.Color;

public record UniqueGiftBackdropColors(
        @JsonProperty(value = "center_color", required = true)
        @NotNull
        Color centerColor,

        @JsonProperty(value = "edge_color", required = true)
        @NotNull
        Color edgeColor,

        @JsonProperty(value = "symbol_color", required = true)
        @NotNull
        Color symbolColor,

        @JsonProperty(value = "text_color", required = true)
        @NotNull
        Color textColor
) implements ApiResult {
}
