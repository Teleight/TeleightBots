package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

import java.awt.Color;

public record CreateForumTopic(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "name", required = true)
        @NotNull
        String name,

        @JsonProperty(value = "color")
        @Nullable
        Color color,

        @JsonProperty(value = "icon_custom_emoji_id")
        @Nullable
        String iconCustomEmojiId
) implements ApiMethodBoolean {

    public static Builder of(String chatId, String name) {
        return new CreateForumTopic.Builder(chatId, name);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "createForumTopic";
    }

    public static class Builder {
        private final String chatId;
        private final String name;
        private Color color;
        private String iconCustomEmojiId;

        Builder(String chatId, String name) {
            this.chatId = chatId;
            this.name = name;
        }

        public Builder color(Color color) {
            this.color = color;
            return this;
        }

        public Builder iconCustomEmojiId(String iconCustomEmojiId) {
            this.iconCustomEmojiId = iconCustomEmojiId;
            return this;
        }

        public CreateForumTopic build() {
            return new CreateForumTopic(this.chatId, this.name, this.color, this.iconCustomEmojiId);
        }
    }
}
