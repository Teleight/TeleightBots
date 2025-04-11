package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record UniqueGiftModel(
        @JsonProperty(value = "name", required = true)
        @NotNull
        String name,
        
        @JsonProperty(value = "sticker", required = true)
        @NotNull
        Sticker sticker,
        
        @JsonProperty(value = "rarity_per_mille", required = true)
        int rarityPerMille
) implements ApiResult {
}
