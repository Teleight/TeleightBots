package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public record PaidMediaPhoto(
        @JsonProperty(value = "photo", required = true)
        @NotNull
        PhotoSize[] photo
) implements PaidMedia {

    @Override
    public PaidMediaType type() {
        return PaidMediaType.PHOTO;
    }

}
