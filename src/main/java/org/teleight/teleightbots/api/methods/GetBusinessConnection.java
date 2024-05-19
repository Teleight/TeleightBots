package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.BusinessConnection;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public record GetBusinessConnection(
        @JsonProperty(value = "business_connection_id", required = true)
        @NotNull
        String businessConnectionId
) implements ApiMethod<BusinessConnection> {

    public static Builder ofBuilder(String businessConnectionId) {
        return new GetBusinessConnection.Builder(businessConnectionId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getBusinessConnection";
    }

    @Override
    public @NotNull BusinessConnection deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, BusinessConnection.class);
    }

    public static class Builder {
        private final String businessConnectionId;

        Builder(String businessConnectionId) {
            this.businessConnectionId = businessConnectionId;
        }

        public GetBusinessConnection build() {
            return new GetBusinessConnection(this.businessConnectionId);
        }
    }
}
