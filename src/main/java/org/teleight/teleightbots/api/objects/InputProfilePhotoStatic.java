package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record InputProfilePhotoStatic(
        @JsonProperty(value = "photo", required = true)
        @NotNull
        InputFile photo
) implements InputProfilePhoto {

    public static @NotNull Builder ofBuilder(InputFile photo) {
        return new InputProfilePhotoStatic.Builder().photo(photo);
    }

    @Override
    public String type() {
        return "static";
    }
}
