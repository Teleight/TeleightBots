package org.teleight.teleightbots.api.methods.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.chat.member.ChatMember;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder
public record GetChatMember(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "user_id", required = true)
        @NotNull
        Long userId
) implements ApiMethod<ChatMember> {

    @Override
    public @NotNull ChatMember deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, ChatMember.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getChatMember";
    }

    public sealed interface Builder permits BuilderImpl {
        @NotNull Builder chatId(@NotNull String chatId);

        @NotNull Builder userId(long userId);

        @NotNull GetChatMember build();
    }

    public static class GetChatMemberBuilder {
        @Tolerate
        public GetChatMemberBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }

}
