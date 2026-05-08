package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.checkerframework.common.value.qual.IntRange;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GetUserPersonalChatMessages(
        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "limit", required = true)
        @IntRange(from = 1, to = 20)
        int limit
) implements ApiMethod<Message[]> {

    public static @NotNull Builder ofBuilder(long userId, @IntRange(from = 1, to = 20) int limit) {
        return new GetUserPersonalChatMessages.Builder()
                .userId(userId)
                .limit(limit);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getUserPersonalChatMessages";
    }

    @Override
    public @NotNull Message @NotNull [] deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Message[].class);
    }
}
