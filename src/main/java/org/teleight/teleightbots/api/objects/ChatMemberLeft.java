package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChatMemberLeft(
        @JsonProperty("user")
        User user
) implements ChatMember {

    @Override
    public String status() {
        return "left";
    }

}
