package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.AcceptedGiftTypes;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetBusinessAccountGiftSettings(
        @JsonProperty(value = "business_connection_id", required = true)
        @NotNull
        String businessConnectionId,

        @JsonProperty(value = "show_gift_button", required = true)
        boolean showGiftButton,

        @JsonProperty(value = "accepted_gift_types", required = true)
        @NotNull
        AcceptedGiftTypes acceptedGiftTypes
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String businessConnectionId, boolean showGiftButton, AcceptedGiftTypes acceptedGiftTypes) {
        return new SetBusinessAccountGiftSettings.Builder()
                .businessConnectionId(businessConnectionId)
                .showGiftButton(showGiftButton)
                .acceptedGiftTypes(acceptedGiftTypes);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setBusinessAccountGiftSettings";
    }
}
