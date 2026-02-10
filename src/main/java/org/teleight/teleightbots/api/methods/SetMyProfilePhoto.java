package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.MultiPartApiMethodBoolean;
import org.teleight.teleightbots.api.objects.InputProfilePhoto;

import java.util.HashMap;
import java.util.Map;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetMyProfilePhoto(
        @JsonProperty(value = "photo", required = true)
        @NotNull
        InputProfilePhoto photo
) implements MultiPartApiMethodBoolean {

    public static @NotNull Builder ofBuilder(@NotNull InputProfilePhoto photo) {
        return new SetMyProfilePhoto.Builder().photo(photo);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setMyProfilePhoto";
    }

    @Override
    public @NotNull Map<String, Object> getParameters() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("photo", photo);
        return parameters;
    }
}
