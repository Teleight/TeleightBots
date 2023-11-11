package org.teleight.teleightbots.api.objects.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.User;

public record ChatInviteLink(
        @JsonProperty("invite_link")
        String inviteLink,

        @JsonProperty("creator")
        User creator,

        @JsonProperty("creates_join_request")
        boolean createsJoinRequest,

        @JsonProperty("is_primary")
        boolean isPrimary,

        @JsonProperty("is_revoked")
        boolean isRevoked,

        @JsonProperty("name")
        @Nullable
        String name,

        @JsonProperty("expire_date")
        @Nullable
        Integer expireDate,

        @JsonProperty("member_limit")
        @Range(from = 1, to = 99999)
        @Nullable
        Integer memberLimit,

        @JsonProperty("pending_join_request_count")
        @Nullable
        Integer pendingJoinRequestCount
) implements ApiResult {
}
