package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record Dice(
        @JsonProperty(value = "emoji", required = true)
        DiceEmoji emoji,

        @JsonProperty(value = "value", required = true)
        Integer value
) implements ApiResult {

    public enum DiceEmoji {

        GAME_DICE("\uD83C\uDFB2"),
        DIRECT_HIT("\uD83C\uDFAF"),
        BASKETBALL("\uD83C\uDFC0"),
        SOCCER_BALL("\\u26BD"),
        BOWLING("\uD83C\uDFB3"),
        SLOT_MACHINE("\uD83C\uDFB0");

        private final String fieldValue;

        DiceEmoji(String fieldValue) {
            this.fieldValue = fieldValue;
        }

        public String getFieldValue() {
            return fieldValue;
        }
    }

}
