package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record UnpinAllGeneralForumTopicMessages(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements ApiMethodBoolean {

    public static Builder of(String chatId) {
        return new UnpinAllGeneralForumTopicMessages.Builder(chatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "unpinAllGeneralForumTopicMessages";
    }

    public static class Builder {
        private final String chatId;

        public Builder(String chatId) {
            this.chatId = chatId;
        }

        public UnpinAllGeneralForumTopicMessages build() {
            return new UnpinAllGeneralForumTopicMessages(this.chatId);
        }
    }
}
