package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaidMediaPreview(
        @JsonProperty("width")
        int width,

        @JsonProperty("height")
        int height,

        @JsonProperty("duration")
        int duration
) implements PaidMedia {

    @Override
    public String type() {
        return "preview";
    }

}
