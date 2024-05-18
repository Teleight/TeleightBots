package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChatMemberMember(
        @JsonProperty("status")
        String status,

        @JsonProperty("user")
        User user
) implements ChatMember {

    @Override
    public ChatMemberType type() {
        return ChatMemberType.MEMBER;
    }

}
