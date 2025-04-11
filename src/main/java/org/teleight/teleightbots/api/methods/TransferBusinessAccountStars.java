package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record TransferBusinessAccountStars(
        @JsonProperty(value = "business_connection_id", required = true)
        @NotNull
        String businessConnectionId,
        
        @JsonProperty(value = "star_count", required = true)
        @Range(from = 0, to = 10_000)
        int starCount
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String businessConnectionId, int starCount) {
        return new TransferBusinessAccountStars.Builder()
                .businessConnectionId(businessConnectionId)
                .starCount(starCount);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "transferBusinessAccountStars";
    }
}
