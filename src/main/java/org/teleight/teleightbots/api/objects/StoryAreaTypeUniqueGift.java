package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public record StoryAreaTypeUniqueGift(
        @JsonProperty(value = "name", required = true)
        @NotNull
        String name
) implements StoryAreaType {

    @Override
    public String type() {
        return "unique_gift";
    }
}
