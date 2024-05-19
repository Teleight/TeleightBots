package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public record ChatBoostSourceGiftCode(
        @JsonProperty(value = "user", required = true)
        @NotNull
        User user
) implements ChatBoostSource {

    @Override
    public ChatBoostSourceType source() {
        return ChatBoostSourceType.GIFT_CODE;
    }

}
