package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record CloseGeneralForumTopic(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements ApiMethodBoolean {

    public static Builder ofBuilder(String chatId) {
        return new CloseGeneralForumTopic.Builder(chatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "closeGeneralForumTopic";
    }

    public static class Builder {
        private final String chatId;

        Builder(String chatId) {
            this.chatId = chatId;
        }

        public CloseGeneralForumTopic build() {
            return new CloseGeneralForumTopic(this.chatId);
        }
    }
}
