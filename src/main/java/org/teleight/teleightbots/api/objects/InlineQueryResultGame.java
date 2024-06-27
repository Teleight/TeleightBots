package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record InlineQueryResultGame(
        @JsonProperty(value = "id", required = true)
        @NotNull
        String id,

        @JsonProperty(value = "game_short_name", required = true)
        @NotNull
        String gameShortName,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements InlineQueryResult {

    @Override
    public InlineQueryResultType type() {
        return InlineQueryResultType.GAME;
    }

}
