package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public record ReactionTypeCustomEmoji(
        @JsonProperty(value = "custom_emoji", required = true)
        @NotNull
        String customEmoji
) implements ReactionType {

    @Override
    public String type() {
        return "custom_emoji";
    }

}
