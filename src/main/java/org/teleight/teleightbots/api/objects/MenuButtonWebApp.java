package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MenuButtonWebApp(
        @JsonProperty(value = "text", required = true)
        String text,

        @JsonProperty(value = "web_app", required = true)
        WebAppInfo webAppInfo
) implements MenuButton {

    public static Builder ofBuilder(String text, WebAppInfo webAppInfo) {
        return new MenuButtonWebApp.Builder(text, webAppInfo);
    }

    @Override
    public MenuButtonType type() {
        return MenuButtonType.WEB_APP;
    }

    public static final class Builder {
        private final String text;
        private final WebAppInfo webAppInfo;

        public Builder(String text, WebAppInfo webAppInfo) {
            this.text = text;
            this.webAppInfo = webAppInfo;
        }

        public MenuButtonWebApp build() {
            return new MenuButtonWebApp(this.text, this.webAppInfo);
        }
    }

}
