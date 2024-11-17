package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.teleight.teleightbots.api.ApiResult;

import java.util.Date;

public record ChatInviteLink(
        @JsonProperty(value = "invite_link", required = true)
        String inviteLink,

        @JsonProperty(value = "creator", required = true)
        User creator,

        @JsonProperty(value = "creates_join_request", required = true)
        boolean createsJoinRequest,

        @JsonProperty(value = "is_primary", required = true)
        boolean isPrimary,

        @JsonProperty(value = "is_revoked", required = true)
        boolean isRevoked,

        @JsonProperty(value = "name")
        @Nullable
        String name,

        @JsonProperty(value = "expire_date")
        @Nullable
        Date expireDate,

        @JsonProperty(value = "member_limit")
        @Range(from = 1, to = 99_999)
        int memberLimit,

        @JsonProperty(value = "pending_join_request_count")
        int pendingJoinRequestCount,

        @JsonProperty(value = "subscription_period")
        int subscriptionPeriod,

        @JsonProperty(value = "subscription_price")
        int subscriptionPrice
) implements ApiResult {
}
