package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public record StoryAreaTypeLink(
        @JsonProperty(value = "url", required = true)
        @NotNull
        String url
) implements StoryAreaType {

    @Override
    public String type() {
        return "link";
    }
}
