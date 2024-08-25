package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChatMemberBanned(
        @JsonProperty("user")
        User user,

        @JsonProperty("until_date")
        int untilDate
) implements ChatMember {

    @Override
    public String status() {
        return "kicked";
    }

}
