package org.teleight.teleightbots.api.methods;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.WebhookInfo;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GetWebhookInfo() implements ApiMethod<WebhookInfo> {

    @Override
    public @NotNull String getEndpointURL() {
        return "getWebhookInfo";
    }

    @Override
    public @NotNull WebhookInfo deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, WebhookInfo.class);
    }

}
