package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record EditGeneralForumTopic(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "name", required = true)
        @NotNull
        String name
) implements ApiMethodBoolean {

    public static Builder ofBuilder(String chatId, String name) {
        return new EditGeneralForumTopic.Builder(chatId, name);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "editGeneralForumTopic";
    }

    public static class Builder {
        private final String chatId;
        private final String name;

        Builder(String chatId, String name) {
            this.chatId = chatId;
            this.name = name;
        }

        public EditGeneralForumTopic build() {
            return new EditGeneralForumTopic(this.chatId, this.name);
        }
    }
}
