package org.teleight.teleightbots.api.objects.chat.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.User;

public record ChatMemberMember(
        @JsonProperty("status")
        String status,

        @JsonProperty("user")
        User user
) implements ChatMember, ApiResult {

        @Override
        public ChatMemberType type() {
                return ChatMemberType.MEMBER;
        }

}
