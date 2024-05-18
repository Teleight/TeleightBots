package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChatMemberBanned(
        @JsonProperty("status")
        String status,

        @JsonProperty("user")
        User user,

        @JsonProperty("until_date")
        int untilDate
) implements ChatMember {

    @Override
    public ChatMemberType type() {
        return ChatMemberType.BANNED;
    }

}
