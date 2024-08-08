package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetChatDescription(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "description", required = true)
        @NotNull
        String description
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String chatId, String description) {
        return new SetChatDescription.Builder().chatId(chatId).description(description);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setChatDescription";
    }

}
