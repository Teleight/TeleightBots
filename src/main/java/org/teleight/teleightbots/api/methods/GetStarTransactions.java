package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.StarTransactions;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GetStarTransactions(
        @JsonProperty("offset")
        int offset,

        @JsonProperty(value = "limit", defaultValue = "100")
        @Range(from = 1, to = 100)
        int limit
) implements ApiMethod<StarTransactions> {

    @Override
    public @NotNull String getEndpointURL() {
        return "getStarTransactions";
    }

    @Override
    public @NotNull StarTransactions deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, StarTransactions.class);
    }

}
