package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public record ChatMemberMember(
        @JsonProperty(value = "tag")
        @Nullable
        String tag,

        @JsonProperty(value = "user", required = true)
        @NotNull
        User user,

        @JsonProperty(value = "until_date")
        @NotNull
        Date untilDate
) implements ChatMember {

    @Override
    public String status() {
        return "member";
    }

}
