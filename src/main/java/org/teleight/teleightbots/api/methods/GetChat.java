package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.ChatFullInfo;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GetChat(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements ApiMethod<ChatFullInfo> {

    public static @NotNull Builder ofBuilder(String chatId) {
        return new GetChat.Builder().chatId(chatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getChat";
    }

    @Override
    public @NotNull ChatFullInfo deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, ChatFullInfo.class);
    }

}
