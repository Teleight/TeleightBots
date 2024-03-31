package org.teleight.teleightbots.api.methods.chat.admin.forum;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder
public record CloseForumTopic(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id", required = true)
        @NotNull
        Long messageThreadId
) implements ApiMethodBoolean {

    @Override
    public @NotNull String getEndpointURL() {
        return "closeForumTopic";
    }

    public static class CloseForumTopicBuilder {
        @Tolerate
        public CloseForumTopic.CloseForumTopicBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
