package org.teleight.teleightbots.api.objects.chat.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.User;

public record ChatMemberOwner(
        @JsonProperty("status")
        String status,

        @JsonProperty("user")
        User user,

        @JsonProperty("is_anonymous")
        boolean isAnonymous,

        @JsonProperty("custom_title")
        @Nullable
        String customTitle
) implements ChatMember, ApiResult {

        @Override
        public ChatMemberType type() {
                return ChatMemberType.OWNER;
        }

}
