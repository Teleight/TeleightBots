package org.teleight.teleightbots.api.methods.chat.admin.forum;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder
public record DeleteForumTopic(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id", required = true)
        @NotNull
        Long messageThreadId
) implements ApiMethodBoolean {

    @Override
    public @NotNull String getEndpointURL() {
        return "deleteForumTopic";
    }

    public static class DeleteForumTopicBuilder {
        @Tolerate
        public DeleteForumTopic.DeleteForumTopicBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
