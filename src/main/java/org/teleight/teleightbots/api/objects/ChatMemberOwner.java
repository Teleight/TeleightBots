package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

public record ChatMemberOwner(
        @JsonProperty("user")
        User user,

        @JsonProperty("is_anonymous")
        boolean isAnonymous,

        @JsonProperty("custom_title")
        @Nullable
        String customTitle
) implements ChatMember {

    @Override
    public String status() {
        return "creator";
    }

}
