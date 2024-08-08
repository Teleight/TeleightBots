package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record EditGeneralForumTopic(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "name", required = true)
        @NotNull
        String name
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String chatId, String name) {
        return new EditGeneralForumTopic.Builder().chatId(chatId).name(name);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "editGeneralForumTopic";
    }

}
