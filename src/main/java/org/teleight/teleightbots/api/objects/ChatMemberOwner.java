package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

public record ChatMemberOwner(
        @JsonProperty(value = "user")
        User user,

        @JsonProperty(value = "is_anonymous")
        boolean isAnonymous,

        @JsonProperty(value = "custom_title")
        @Nullable
        String customTitle
) implements ChatMember {

    @Override
    public String status() {
        return "creator";
    }

}
