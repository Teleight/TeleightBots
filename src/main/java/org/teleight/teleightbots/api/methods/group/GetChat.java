package org.teleight.teleightbots.api.methods.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.chat.Chat;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public record GetChat(
        @JsonProperty(value = "chat_id", required = true)
        String chatId
) implements ApiMethod<Chat> {

     public static @NotNull Builder builder() {
        return new BuilderImpl();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getChat";
    }

    public sealed interface Builder permits BuilderImpl {
        @NotNull Builder chatId(@NotNull String chatId);

        @NotNull GetChat build();
    }


    static final class BuilderImpl implements Builder {

         private String chatId;

        @Override
        public @NotNull Builder chatId(@NotNull String chatId) {
            this.chatId = chatId;
            return this;
        }

        @Override
        public @NotNull GetChat build() {
            return new GetChat(chatId);
        }
    }

    @Override
    public @NotNull Chat deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Chat.class);
    }

}
