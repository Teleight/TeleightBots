package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.ChatAdministratorRights;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GetMyDefaultAdministratorRights(
        @JsonProperty(value = "for_channels")
        boolean forChannels
) implements ApiMethod<ChatAdministratorRights> {

    @Override
    public @NotNull String getEndpointURL() {
        return "getMyDefaultAdministratorRights";
    }

    @Override
    public @NotNull ChatAdministratorRights deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, ChatAdministratorRights.class);
    }

}
