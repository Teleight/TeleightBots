package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.ChatInviteLink;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

import java.util.Date;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record CreateChatSubscriptionInviteLink(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty("name")
        @Nullable
        String name,

        @JsonProperty(value = "subscription_period", required = true)
        @NotNull
        Date subscriptionPeriod,

        @JsonProperty(value = "subscription_price", required = true)
        @Range(from = 0, to = 2_500)
        @NotNull
        Date subscriptionPrice
) implements ApiMethod<ChatInviteLink> {

    public static @NotNull Builder ofBuilder(String chatId, Date subscriptionPrice) {
        return new CreateChatSubscriptionInviteLink.Builder()
                .chatId(chatId)
                .subscriptionPeriod(new Date(2592000))
                .subscriptionPrice(subscriptionPrice);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "createChatSubscriptionInviteLink";
    }

    @Override
    public @NotNull ChatInviteLink deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, ChatInviteLink.class);
    }

}
