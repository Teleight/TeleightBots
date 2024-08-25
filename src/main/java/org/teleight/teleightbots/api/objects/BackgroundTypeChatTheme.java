package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public record BackgroundTypeChatTheme(
        @JsonProperty(value = "theme_name", required = true)
        @NotNull
        String themeName
) implements BackgroundType {

    @Override
    public String type() {
        return "chat_theme";
    }

}
