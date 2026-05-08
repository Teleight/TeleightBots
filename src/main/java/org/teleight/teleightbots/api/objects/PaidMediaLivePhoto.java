package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public record PaidMediaLivePhoto(
        @JsonProperty(value = "live_photo", required = true)
        @NotNull
        LivePhoto livePhoto
) implements PaidMedia {

    @Override
    public String type() {
        return "live_photo";
    }

}
