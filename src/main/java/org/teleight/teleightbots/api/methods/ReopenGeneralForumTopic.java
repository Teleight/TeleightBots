package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record ReopenGeneralForumTopic(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements ApiMethodBoolean {

    public static Builder of(String chatId) {
        return new ReopenGeneralForumTopic.Builder(chatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "reopenGeneralForumTopic";
    }

    public static class Builder {
        private final String chatId;

        Builder(String chatId) {
            this.chatId = chatId;
        }

        public ReopenGeneralForumTopic build() {
            return new ReopenGeneralForumTopic(this.chatId);
        }
    }
}
