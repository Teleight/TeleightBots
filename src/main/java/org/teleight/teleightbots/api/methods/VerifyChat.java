package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record VerifyChat(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "custom_description")
        @Nullable
        String customDescription // 0-70 characters
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String chatId) {
        return new VerifyChat.Builder().chatId(chatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "verifyChat";
    }

}
