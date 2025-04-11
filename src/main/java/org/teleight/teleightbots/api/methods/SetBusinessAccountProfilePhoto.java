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
public record SetBusinessAccountProfilePhoto(
        @JsonProperty(value = "business_connection_id", required = true)
        @NotNull
        String businessConnectionId,

        @JsonProperty(value = "photo", required = true)
        @NotNull
        InputProfilePhoto photo,

        @JsonProperty(value = "is_public")
        boolean isPublic
) implements MultiPartApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String businessConnectionId, InputProfilePhoto photo) {
        return new SetBusinessAccountProfilePhoto.Builder()
                .businessConnectionId(businessConnectionId)
                .photo(photo);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setBusinessAccountProfilePhoto";
    }

    @Override
    public @NotNull Map<String, Object> getParameters() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("business_connection_id", businessConnectionId);
        parameters.put("photo", photo);
        parameters.put("is_public", isPublic);
        return parameters;
    }
}
