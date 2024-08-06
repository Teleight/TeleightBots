package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record DeclineChatJoinRequest(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "user_id", required = true)
        long userId
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String chatId, long userId) {
        return new DeclineChatJoinRequest.Builder().chatId(chatId).userId(userId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "declineChatJoinRequest";
    }

}
