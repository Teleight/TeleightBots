package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

public record ChatMemberOwner(
        @JsonProperty("status")
        String status,

        @JsonProperty("user")
        User user,

        @JsonProperty("is_anonymous")
        boolean isAnonymous,

        @JsonProperty("custom_title")
        @Nullable
        String customTitle
) implements ChatMember {

    @Override
    public ChatMemberType type() {
        return ChatMemberType.OWNER;
    }

}
