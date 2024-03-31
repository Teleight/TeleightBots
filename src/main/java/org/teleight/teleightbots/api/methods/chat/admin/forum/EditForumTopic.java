package org.teleight.teleightbots.api.methods.chat.admin.forum;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder
public record EditForumTopic(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id", required = true)
        @NotNull
        Long messageThreadId,

        @JsonProperty(value = "name")
        @NotNull
        String name,

        @JsonProperty(value = "icon_custom_emoji_id")
        String iconCustomEmojiId
) implements ApiMethodBoolean {

    @Override
    public @NotNull String getEndpointURL() {
        return "editForumTopic";
    }

    public static class EditForumTopicBuilder {
        @Tolerate
        public EditForumTopic.EditForumTopicBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
