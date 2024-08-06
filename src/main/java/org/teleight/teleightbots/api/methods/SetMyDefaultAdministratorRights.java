package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.ChatAdministratorRights;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetMyDefaultAdministratorRights(
        @JsonProperty(value = "rights")
        @Nullable
        ChatAdministratorRights rights,

        @JsonProperty(value = "for_channels")
        boolean forChannels
) implements ApiMethodBoolean {

    @Override
    public @NotNull String getEndpointURL() {
        return "setMyDefaultAdministratorRights";
    }

}
