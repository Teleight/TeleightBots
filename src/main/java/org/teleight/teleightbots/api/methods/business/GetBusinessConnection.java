package org.teleight.teleightbots.api.methods.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.business.BusinessConnection;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder
public record GetBusinessConnection(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String business_connection_id
) implements ApiMethod<BusinessConnection> {

    @Override
    public @NotNull BusinessConnection deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, BusinessConnection.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getBusinessConnection";
    }

}
