package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record InputPaidMediaLivePhoto(
        @JsonProperty(value = "media", required = true)
        @NotNull
        InputFile media,

        @JsonProperty(value = "photo", required = true)
        @NotNull
        InputFile photo
) implements InputPaidMedia {

    public static @NotNull Builder ofBuilder(InputFile media, InputFile photo) {
        return new InputPaidMediaLivePhoto.Builder().media(media).photo(photo);
    }

    @Override
    public String type() {
        return "live_photo";
    }

}
