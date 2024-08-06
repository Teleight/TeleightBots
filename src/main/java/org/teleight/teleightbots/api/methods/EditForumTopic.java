package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
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

    public static @NotNull Builder ofBuilder(String chatId, int messageThreadId) {
        return new EditForumTopic.Builder().chatId(chatId).messageThreadId(messageThreadId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "editForumTopic";
    }

}
