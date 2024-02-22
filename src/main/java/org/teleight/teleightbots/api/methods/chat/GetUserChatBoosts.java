package org.teleight.teleightbots.api.methods.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.chat.boost.UserChatBoosts;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder
public record GetUserChatBoosts(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "user_id", required = true)
        @NotNull
        Long userId
) implements ApiMethod<UserChatBoosts> {

    @Override
    public @NotNull UserChatBoosts deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, UserChatBoosts.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getUserChatBoosts";
    }

    public static class GetUserChatBoostsBuilder {
        @Tolerate
        public GetUserChatBoostsBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
