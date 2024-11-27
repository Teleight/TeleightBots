package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChatMemberBanned(
        @JsonProperty(value = "user")
        User user,

        @JsonProperty(value = "until_date")
        int untilDate
) implements ChatMember {

    @Override
    public String status() {
        return "kicked";
    }

}
