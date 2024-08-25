package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public record ReactionTypeEmoji(
        @JsonProperty(value = "emoji", required = true)
        @NotNull
        String emoji
) implements ReactionType {

    @Override
    public String type() {
        return "emoji";
    }

}
