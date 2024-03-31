package org.teleight.teleightbots.api.methods.chat.admin.moderate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder
public record UnbanChatMember(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "user_id", required = true)
        @NotNull
        Long userId,

        @JsonProperty(value = "only_if_banned")
        @Nullable
        Boolean onlyIfBanned
) implements ApiMethodBoolean {
    @Override
    public @NotNull String getEndpointURL() {
        return "unbanChatMember";
    }

    public static class UnbanChatMemberBuilder {
        @Tolerate
        public UnbanChatMemberBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
