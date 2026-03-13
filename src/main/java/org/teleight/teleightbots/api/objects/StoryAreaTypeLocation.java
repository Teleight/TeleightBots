package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

public record StoryAreaTypeLocation(
        @JsonProperty(value = "latitude", required = true)
        float latitude,

        @JsonProperty(value = "longitude", required = true)
        float longitude,

        @JsonProperty(value = "address")
        @Nullable
        LocationAddress address
) implements StoryAreaType {

    @Override
    public String type() {
        return "location";
    }
}
