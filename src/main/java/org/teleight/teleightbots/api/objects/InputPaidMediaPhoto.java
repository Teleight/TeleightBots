package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record InputPaidMediaPhoto(
        @JsonProperty(value = "media", required = true)
        @NotNull
        InputFile media
) implements InputPaidMedia {

    public static @NotNull Builder ofBuilder(InputFile media) {
        return new InputPaidMediaPhoto.Builder().media(media);
    }

    @Override
    public String type() {
        return "photo";
    }

}
