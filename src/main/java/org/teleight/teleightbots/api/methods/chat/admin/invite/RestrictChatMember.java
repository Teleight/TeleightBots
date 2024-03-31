package org.teleight.teleightbots.api.methods.chat.admin.invite;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.chat.ChatPermissions;

@Builder
public record RestrictChatMember(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "user_id", required = true)
        @NotNull
        Long userId,

        @JsonProperty(value = "permissions", required = true)
        @NotNull
        ChatPermissions permissions,

        @JsonProperty(value = "use_independent_chat_permissions")
        @Nullable
        Boolean useIndependentChatPermissions,

        @JsonProperty(value = "until_date")
        @Nullable
        Integer untilDate
) implements ApiMethodBoolean {
    @Override
    public @NotNull String getEndpointURL() {
        return "restrictChatMember";
    }

    public static class RestrictChatMemberBuilder {
        @Tolerate
        public RestrictChatMemberBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
