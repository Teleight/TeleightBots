package org.teleight.teleightbots.api.objects.chat.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.User;

public record ChatMemberBanned(
        @JsonProperty("status")
        String status,

        @JsonProperty("user")
        User user,

        @JsonProperty("until_date")
        int untilDate
) implements ChatMember, ApiResult {

        @Override
        public ChatMemberType type() {
                return ChatMemberType.BANNED;
        }

}
