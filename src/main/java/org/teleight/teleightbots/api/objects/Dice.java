package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.serialization.SimpleFieldValueProvider;

public record Dice(
        @JsonProperty(value = "emoji", required = true)
        DiceEmoji emoji,

        @JsonProperty(value = "value", required = true)
        int value
) implements ApiResult {

    public enum DiceEmoji implements SimpleFieldValueProvider {

        GAME_DICE("\\ud83c\\udfb2"),
        DIRECT_HIT("\\ud83c\\udfaf"),
        BASKETBALL("\\ud83c\\udfc0"),
        SOCCER_BALL("\\u26bd"),
        BOWLING("\\ud83c\\udfb3"),
        SLOT_MACHINE("\\ud83c\\udfb0");

        private final String fieldValue;

        DiceEmoji(String fieldValue) {
            this.fieldValue = fieldValue;
        }

        @Override
        public String getFieldValue() {
            return fieldValue;
        }
    }

}
