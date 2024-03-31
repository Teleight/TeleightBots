package org.teleight.teleightbots.api.methods.chat.admin.moderate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder
public record BanChatMember(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "user_id", required = true)
        @NotNull
        Long userId,

        @JsonProperty(value = "until_date")
        @Nullable
        Integer untilDate,

        @JsonProperty(value = "revoke_messages")
        @Nullable
        Boolean revokeMessages
) implements ApiMethodBoolean {
    @Override
    public @NotNull String getEndpointURL() {
        return "banChatMember";
    }

    public static class BanChatMemberBuilder {
        @Tolerate
        public BanChatMemberBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
