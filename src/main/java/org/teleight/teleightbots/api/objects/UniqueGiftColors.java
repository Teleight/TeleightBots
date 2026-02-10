package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

import java.awt.Color;

public record UniqueGiftColors(
        @JsonProperty(value = "model_custom_emoji_id", required = true)
        String modelCustomEmojiId,

        @JsonProperty(value = "symbol_custom_emoji_id", required = true)
        String symbolCustomEmojiId,

        @JsonProperty(value = "light_theme_main_color", required = true)
        Color lightThemeMainColor,

        @JsonProperty(value = "light_theme_other_colors", required = true)
        Color[] lightThemeOtherColors,

        @JsonProperty(value = "dark_theme_main_color", required = true)
        Color darkThemeMainColor,

        @JsonProperty(value = "dark_theme_other_colors", required = true)
        Color[] darkThemeOtherColors
) implements ApiResult {
}
