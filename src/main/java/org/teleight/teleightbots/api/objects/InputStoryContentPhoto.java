package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public record InputStoryContentPhoto(
        @JsonProperty(value = "photo", required = true)
        @NotNull
        InputFile photo
) implements InputStoryContent {

    @Override
    public String type() {
        return "photo";
    }
}
