package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record UpgradeGift(
        @JsonProperty(value = "business_connection_id", required = true)
        @NotNull
        String businessConnectionId,

        @JsonProperty(value = "owned_gift_id", required = true)
        @NotNull
        String ownedGiftId,

        @JsonProperty(value = "keep_original_details")
        boolean keepOriginalDetails,

        @JsonProperty(value = "star_count")
        @Nullable
        Integer starCount
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String businessConnectionId, String ownedGiftId) {
        return new UpgradeGift.Builder()
                .businessConnectionId(businessConnectionId)
                .ownedGiftId(ownedGiftId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "upgradeGift";
    }
}
