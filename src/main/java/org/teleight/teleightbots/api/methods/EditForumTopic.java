package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record EditForumTopic(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id", required = true)
        int messageThreadId,

        @JsonProperty(value = "name")
        @NotNull
        String name,

        @JsonProperty(value = "icon_custom_emoji_id")
        String iconCustomEmojiId
) implements ApiMethodBoolean {

    public static Builder ofBuilder(String chatId, int messageThreadId) {
        return new EditForumTopic.Builder(chatId, messageThreadId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "editForumTopic";
    }

    public static class Builder {
        private final String chatId;
        private final int messageThreadId;
        private String name;
        private String iconCustomEmojiId;

        Builder(String chatId, int messageThreadId) {
            this.chatId = chatId;
            this.messageThreadId = messageThreadId;
        }

        public Builder name(@NotNull String name) {
            this.name = name;
            return this;
        }

        public Builder iconCustomEmojiId(String iconCustomEmojiId) {
            this.iconCustomEmojiId = iconCustomEmojiId;
            return this;
        }

        public EditForumTopic build() {
            return new EditForumTopic(this.chatId, this.messageThreadId, this.name, this.iconCustomEmojiId);
        }
    }
}
