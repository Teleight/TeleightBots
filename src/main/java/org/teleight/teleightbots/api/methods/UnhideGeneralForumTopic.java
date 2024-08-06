package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record UnhideGeneralForumTopic(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String chatId) {
        return new UnhideGeneralForumTopic.Builder().chatId(chatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "unhideGeneralForumTopic";
    }

}
