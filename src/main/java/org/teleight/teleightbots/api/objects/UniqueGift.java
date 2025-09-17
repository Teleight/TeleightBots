package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record UniqueGift(
        @JsonProperty(value = "base_name", required = true)
        @NotNull
        String baseName,
        
        @JsonProperty(value = "name", required = true)
        @NotNull
        String name,
        
        @JsonProperty(value = "number", required = true)
        int number,
        
        @JsonProperty(value = "model", required = true)
        @NotNull
        UniqueGiftModel model,
        
        @JsonProperty(value = "symbol", required = true)
        @NotNull
        UniqueGiftSymbol symbol,
        
        @JsonProperty(value = "backdrop", required = true)
        @NotNull
        UniqueGiftBackdrop backdrop,

        @JsonProperty(value = "publisher_chat")
        @Nullable
        Chat publisherChat
) implements ApiResult {
}
