package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaidMediaPreview(
        @JsonProperty(value = "width")
        int width,

        @JsonProperty(value = "height")
        int height,

        @JsonProperty(value = "duration")
        int duration
) implements PaidMedia {

    @Override
    public String type() {
        return "preview";
    }

}
