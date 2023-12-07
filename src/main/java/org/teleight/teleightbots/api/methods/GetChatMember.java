package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.chat.member.ChatMember;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public record GetChatMember(
        @JsonProperty(value = "chat_id",required = true)
        String chatId,

        @JsonProperty(value = "user_id", required = true)
        long userId
) implements ApiMethod<ChatMember> {

    public static @NotNull Builder builder() {
        return new BuilderImpl();
    }

    @Override
    public @NotNull ChatMember deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, ChatMember.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "GetChatMember";
    }

    public sealed interface Builder permits BuilderImpl {
        @NotNull Builder chatId(@NotNull String chatId);

        @NotNull Builder userId(long userId);

        @NotNull GetChatMember build();
    }

    static final class BuilderImpl implements Builder {
        private String chatId;
        private long userId;

        @Override
        public @NotNull Builder chatId(@NotNull String chatId) {
            this.chatId = chatId;
            return this;
        }

        @Override
        public @NotNull Builder userId(long userId) {
            this.userId = userId;
            return this;
        }

        @Override
        public @NotNull GetChatMember build() {
            return new GetChatMember(chatId, userId);
        }
    }

}
