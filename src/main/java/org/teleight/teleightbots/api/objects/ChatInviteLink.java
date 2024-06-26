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

        @JsonProperty("name")
        @Nullable
        String name,

        @JsonProperty("expire_date")
        @Nullable
        Date expireDate,

        @JsonProperty("member_limit")
        @Range(from = 1, to = 99999)
        int memberLimit,

        @JsonProperty("pending_join_request_count")
        int pendingJoinRequestCount
) implements ApiResult {
}
