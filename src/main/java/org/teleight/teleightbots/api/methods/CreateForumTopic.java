package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

import java.awt.Color;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
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

    public static @NotNull Builder ofBuilder(String chatId, String name) {
        return new CreateForumTopic.Builder().chatId(chatId).name(name);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "createForumTopic";
    }

}
