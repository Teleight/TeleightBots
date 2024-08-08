package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.BotCommand;
import org.teleight.teleightbots.api.objects.BotCommandScope;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GetMyCommands(
        @JsonProperty(value = "scope")
        @Nullable
        BotCommandScope scope,

        @JsonProperty(value = "language_code")
        @Nullable
        String languageCode
) implements ApiMethod<BotCommand[]> {

    @Override
    public @NotNull String getEndpointURL() {
        return "getMyCommands";
    }

    @Override
    public BotCommand @NotNull [] deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponseArray(answer, BotCommand[].class);
    }

}
