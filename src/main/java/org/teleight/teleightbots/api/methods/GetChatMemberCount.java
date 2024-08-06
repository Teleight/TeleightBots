package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GetChatMemberCount(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements ApiMethod<Integer> {

    public static @NotNull Builder ofBuilder(String chatId) {
        return new GetChatMemberCount.Builder().chatId(chatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getChatMemberCount";
    }

    @Override
    public @NotNull Integer deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Integer.class);
    }

}
