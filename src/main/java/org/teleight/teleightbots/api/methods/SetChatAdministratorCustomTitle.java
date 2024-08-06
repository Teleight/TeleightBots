package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetChatAdministratorCustomTitle(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "custom_title", required = true)
        @NotNull
        String customTitle
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String chatId, long userId, String customTitle) {
        return new SetChatAdministratorCustomTitle.Builder().chatId(chatId).userId(userId).customTitle(customTitle);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setChatAdministratorCustomTitle";
    }

}
