package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.BotName;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GetMyName(
        @JsonProperty(value = "language_code")
        @Nullable
        String languageCode
) implements ApiMethod<BotName> {

    @Override
    public @NotNull String getEndpointURL() {
        return "getMyName";
    }

    @Override
    public @NotNull BotName deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, BotName.class);
    }

}
