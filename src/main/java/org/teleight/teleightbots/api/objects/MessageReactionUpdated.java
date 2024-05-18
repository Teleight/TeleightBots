package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record MessageReactionUpdated(
        @JsonProperty(value = "chat", required = true)
        @NotNull
        Chat chat,

        @JsonProperty(value = "message_id", required = true)
        @NotNull
        Integer messageId,

        @JsonProperty(value = "user")
        @Nullable
        User user,

        @JsonProperty(value = "actor_chat")
        @Nullable
        Chat actorChat,

        @JsonProperty(value = "date", required = true)
        @NotNull
        Integer date,

        @JsonProperty(value = "old_reaction", required = true)
        @NotNull
        ReactionType[] oldReaction,

        @JsonProperty(value = "new_reaction", required = true)
        @NotNull
        ReactionType[] newReaction
) implements ApiResult {
}
