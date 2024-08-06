package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record MenuButtonWebApp(
        @JsonProperty(value = "text", required = true)
        String text,

        @JsonProperty(value = "web_app", required = true)
        WebAppInfo webAppInfo
) implements MenuButton {

    public static @NotNull Builder ofBuilder(String text, WebAppInfo webAppInfo) {
        return new MenuButtonWebApp.Builder().text(text).webAppInfo(webAppInfo);
    }

    @Override
    public MenuButtonType type() {
        return MenuButtonType.WEB_APP;
    }

}
