package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.ChatPermissions;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record RestrictChatMember(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "permissions", required = true)
        @NotNull
        ChatPermissions permissions,

        @JsonProperty(value = "use_independent_chat_permissions")
        boolean useIndependentChatPermissions,

        @JsonProperty(value = "until_date")
        int untilDate
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String chatId, long userId, ChatPermissions permissions) {
        return new Builder().chatId(chatId).userId(userId).permissions(permissions);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "restrictChatMember";
    }

}
