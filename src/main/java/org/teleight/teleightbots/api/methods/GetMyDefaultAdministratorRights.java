package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.ChatAdministratorRights;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public record GetMyDefaultAdministratorRights(
        @JsonProperty(value = "for_channels")
        @Nullable
        Boolean forChannels
) implements ApiMethod<ChatAdministratorRights> {

    public static Builder ofBuilder() {
        return new GetMyDefaultAdministratorRights.Builder();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getMyDefaultAdministratorRights";
    }

    @Override
    public @NotNull ChatAdministratorRights deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, ChatAdministratorRights.class);
    }

    public static class Builder {
        private boolean forChannels;

        public Builder forChannels(boolean forChannels) {
            this.forChannels = forChannels;
            return this;
        }

        public GetMyDefaultAdministratorRights build() {
            return new GetMyDefaultAdministratorRights(this.forChannels);
        }
    }
}
