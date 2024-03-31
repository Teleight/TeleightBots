package org.teleight.teleightbots.api.methods.chat.admin.forum;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

import java.awt.*;

@Builder
public record CreateForumTopic(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "name", required = true)
        @NotNull
        String name,

        @JsonProperty(value = "color")
        Color color,

        @JsonProperty(value = "icon_custom_emoji_id")
        String iconCustomEmojiId
) implements ApiMethodBoolean {

    @Override
    public @NotNull String getEndpointURL() {
        return "createForumTopic";
    }

    public static class CreateForumTopicBuilder {
        @Tolerate
        public CreateForumTopic.CreateForumTopicBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
        @Tolerate
        public CreateForumTopic.CreateForumTopicBuilder color(Integer rgb) {
            this.color = new Color(rgb);
            return this;
        }
    }
}
