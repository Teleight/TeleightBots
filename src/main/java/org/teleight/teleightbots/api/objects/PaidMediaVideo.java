package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public record PaidMediaVideo(
        @JsonProperty(value = "video", required = true)
        @NotNull
        Video video
) implements PaidMedia {

    @Override
    public PaidMediaType type() {
        return PaidMediaType.VIDEO;
    }

}
