package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.event.keyboard.ButtonPressEvent;
import org.teleight.teleightbots.menu.Menu;

import java.util.UUID;
import java.util.function.BiConsumer;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record InlineKeyboardButton(
        @JsonProperty(value = "text", required = true)
        @NotNull
        String text,

        @JsonProperty("url")
        @Nullable
        String url,

        @JsonProperty(value = "callback_data")
        @Nullable
        String callbackData,

        @JsonProperty("web_app")
        @Nullable
        WebAppInfo webApp,

        @JsonIgnore
        Menu destinationMenu,

        @JsonIgnore
        BiConsumer<ButtonPressEvent, User> callback
) implements ApiResult {

    public static @NotNull Builder ofBuilder(String text) {
        return new InlineKeyboardButton.Builder()
                .callbackData(UUID.randomUUID().toString())
                .text(text);
    }

}
