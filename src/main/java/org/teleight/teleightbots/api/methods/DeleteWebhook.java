package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record DeleteWebhook(
        @JsonProperty(value = "drop_pending_updates")
        boolean dropPendingUpdates
) implements ApiMethodBoolean {

    @Override
    public @NotNull String getEndpointURL() {
        return "deleteWebhook";
    }

}
