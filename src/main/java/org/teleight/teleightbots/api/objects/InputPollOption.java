package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record InputPollOption(
        @JsonProperty(value = "text", required = true)
        @NotNull
        String text,

        @JsonProperty("text_parse_mode")
        @Nullable
        ParseMode textParseMode,

        @JsonProperty("text_entities")
        @Nullable
        MessageEntity[] textEntities
) implements ApiResult {

    public static InputPollOption.Builder ofBuilder(String text) {
        return new InputPollOption.Builder(text);
    }

    public static class Builder {
        private final String text;
        private ParseMode textParseMode;
        private MessageEntity[] textEntities;

        public Builder(String text) {
            this.text = text;
        }

        public InputPollOption.Builder textParseMode(ParseMode textParseMode) {
            this.textParseMode = textParseMode;
            return this;
        }

        public InputPollOption.Builder textEntities(MessageEntity[] textEntities) {
            this.textEntities = textEntities;
            return this;
        }

        public InputPollOption build() {
            return new InputPollOption(this.text, this.textParseMode, this.textEntities);
        }
    }

}
