package org.teleight.teleightbots.api.objects.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.User;

import java.util.Date;

public record ChatInviteLink(
        @JsonProperty(value = "invite_link", required = true)
        String inviteLink,

        @JsonProperty(value = "creator", required = true)
        User creator,

        @JsonProperty(value = "creates_join_request", required = true)
        Boolean createsJoinRequest,

        @JsonProperty(value = "is_primary", required = true)
        Boolean isPrimary,

        @JsonProperty(value = "is_revoked", required = true)
        Boolean isRevoked,

        @JsonProperty("name")
        @Nullable
        String name,

        @JsonProperty("expire_date")
        @Nullable
        Date expireDate,

        @JsonProperty("member_limit")
        @Range(from = 1, to = 99999)
        @Nullable
        Integer memberLimit,

        @JsonProperty("pending_join_request_count")
        @Nullable
        Integer pendingJoinRequestCount
) implements ApiResult {
}
