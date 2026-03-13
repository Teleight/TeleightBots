package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.methods.CopyTextButton;
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

        @JsonProperty(value = "icon_custom_emoji_id")
        @Nullable
        String iconCustomEmojiId,

        @JsonProperty(value = "style")
        @Nullable
        KeyboardButtonStyle style,

        @JsonProperty(value = "url")
        @Nullable
        String url,

        @JsonProperty(value = "callback_data")
        @Nullable
        String callbackData,

        @JsonProperty(value = "web_app")
        @Nullable
        WebAppInfo webApp,

        @JsonProperty(value = "login_url")
        @Nullable
        LoginUrl loginUrl,

        @JsonProperty(value = "switch_inline_query")
        @Nullable
        String switchInlineQuery,

        @JsonProperty(value = "switch_inline_query_current_chat")
        @Nullable
        String switchInlineQueryCurrentChat,

        @JsonProperty(value = "switch_inline_query_chosen_chat")
        @Nullable
        SwitchInlineQueryChosenChat switchInlineQueryChosenChat,

        @JsonProperty(value = "copy_text")
        @Nullable
        CopyTextButton copyText,

        @JsonProperty(value = "callback_game")
        @Nullable
        CallbackGame callbackGame,

        @JsonProperty(value = "pay")
        boolean pay,

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
