package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record BanChatMember(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "until_date")
        int untilDate,

        @JsonProperty(value = "revoke_messages")
        boolean revokeMessages
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String chatId, long userId) {
        return new BanChatMember.Builder().chatId(chatId).userId(userId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "banChatMember";
    }

}
