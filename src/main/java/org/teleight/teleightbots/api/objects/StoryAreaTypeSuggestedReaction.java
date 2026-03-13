package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public record StoryAreaTypeSuggestedReaction(
        @JsonProperty(value = "reaction_type", required = true)
        @NotNull
        ReactionType reactionType,

        @JsonProperty(value = "is_dark")
        boolean isDark,

        @JsonProperty(value = "is_flipped")
        boolean isFlipped
) implements StoryAreaType {

    @Override
    public String type() {
        return "suggested_reaction";
    }
}
