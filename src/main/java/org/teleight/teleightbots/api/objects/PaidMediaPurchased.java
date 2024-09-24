package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public record PaidMediaPurchased(
        @JsonProperty(value = "from", required = true)
        @NotNull
        User from,

        @JsonProperty(value = "paid_media_payload", required = true)
        @NotNull
        String paidMediaPayload
) {
}
