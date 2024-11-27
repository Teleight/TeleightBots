package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record EditUserStarSubscription(
        @JsonProperty(value = "user_id", required = true)
        int userId,

        @JsonProperty(value = "telegram_payment_charge_id", required = true)
        @NotNull
        String telegramPaymentChargeId,

        @JsonProperty(value = "is_canceled", required = true)
        boolean isCanceled
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(int userId, String telegramPaymentChargeId, boolean isCanceled) {
        return new EditUserStarSubscription.Builder()
                .userId(userId)
                .telegramPaymentChargeId(telegramPaymentChargeId)
                .isCanceled(isCanceled);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "editUserStarSubscription";
    }

}
