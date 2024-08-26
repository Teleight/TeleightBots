package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
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

    public static @NotNull Builder ofBuilder(String id, String gameShortName) {
        return new InlineQueryResultGame.Builder().id(id).gameShortName(gameShortName);
    }

    @Override
    public String type() {
        return "game";
    }

}
