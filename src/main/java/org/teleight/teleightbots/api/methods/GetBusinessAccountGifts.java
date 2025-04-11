package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.OwnedGifts;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GetBusinessAccountGifts(
        @JsonProperty(value = "business_connection_id", required = true)
        @NotNull
        String businessConnectionId,

        @JsonProperty(value = "exclude_unsaved")
        boolean excludeUnsaved,

        @JsonProperty(value = "exclude_saved")
        boolean excludeSaved,

        @JsonProperty(value = "exclude_unlimited")
        boolean excludeUnlimited,

        @JsonProperty(value = "exclude_limited")
        boolean excludeLimited,

        @JsonProperty(value = "exclude_unique")
        boolean excludeUnique,

        @JsonProperty(value = "sort_by_price")
        boolean sortByPrice,

        @JsonProperty(value = "offset")
        @Nullable
        String offset,

        @JsonProperty(value = "limit", defaultValue = "100")
        @Range(from = 1, to = 100)
        int limit
) implements ApiMethod<OwnedGifts> {

    public static @NotNull Builder ofBuilder(String businessConnectionId) {
        return new GetBusinessAccountGifts.Builder()
                .businessConnectionId(businessConnectionId);
    }

    @Override
    public @NotNull OwnedGifts deserializeResponse(@NotNull String answer) {
        return deserializeResponse(answer, OwnedGifts.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getBusinessAccountGifts";
    }
}
