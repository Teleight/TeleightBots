package org.teleight.teleightbots.api.objects.menubutton;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.webapp.WebAppInfo;

public record MenuButtonWebApp(
        @JsonProperty(value = "text", required = true)
        String text,

        @JsonProperty(value = "web_app", required = true)
        WebAppInfo webAppInfo
) implements MenuButton {

    public static @NotNull Builder builder() {
        return new BuilderImpl();
    }

    @Override
    public String type() {
        return "web_app";
    }

    public sealed interface Builder permits BuilderImpl {
        @NotNull Builder text(@NotNull String text);

        @NotNull Builder webAppInfo(@NotNull WebAppInfo webAppInfo);

        @NotNull MenuButtonWebApp build();
    }

    static final class BuilderImpl implements Builder {
        private String text;
        private WebAppInfo webAppInfo;

        @Override
        public @NotNull Builder text(@NotNull String text) {
            this.text = text;
            return this;
        }

        @Override
        public @NotNull Builder webAppInfo(@NotNull WebAppInfo webAppInfo) {
            this.webAppInfo = webAppInfo;
            return this;
        }

        @Override
        public @NotNull MenuButtonWebApp build() {
            return new MenuButtonWebApp(text, webAppInfo);
        }
    }

}
