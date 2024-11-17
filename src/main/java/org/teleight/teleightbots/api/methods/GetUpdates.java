package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.bot.settings.LongPollingBotSettings;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

import java.util.List;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GetUpdates(
        @JsonProperty(value = "offset")
        int offset,

        @JsonProperty(value = "limit")
        int limit,

        @JsonProperty(value = "timeout")
        int timeout,

        @JsonProperty(value = "allowed_updates")
        @Nullable
        List<String> allowedUpdates
) implements ApiMethod<Update[]> {

    @Override
    public Update @NotNull [] deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponseArray(answer, Update.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getUpdates";
    }

    public static class Builder {
        Builder() {
            limit = LongPollingBotSettings.DEFAULT.updatesTimeout();
            timeout = LongPollingBotSettings.DEFAULT.updatesTimeout();
        }
    }

}
