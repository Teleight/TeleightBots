package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record HideGeneralForumTopic(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements ApiMethodBoolean {

    public static Builder ofBuilder(String chatId) {
        return new HideGeneralForumTopic.Builder(chatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "hideGeneralForumTopic";
    }

    public static class Builder {
        private final String chatId;

        public Builder(String chatId) {
            this.chatId = chatId;
        }

        public HideGeneralForumTopic build() {
            return new HideGeneralForumTopic(this.chatId);
        }
    }
}
