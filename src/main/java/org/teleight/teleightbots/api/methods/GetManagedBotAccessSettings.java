package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.ApiMethodString;
import org.teleight.teleightbots.api.objects.BotAccessSettings;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GetManagedBotAccessSettings(
        @JsonProperty(value = "user_id", required = true)
        long userId
) implements ApiMethod<BotAccessSettings> {

    public static @NotNull Builder ofBuilder(long userId) {
        return new GetManagedBotAccessSettings.Builder()
                .userId(userId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getManagedBotAccessSettings";
    }

    @Override
    public @NotNull BotAccessSettings deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, BotAccessSettings.class);
    }
}
