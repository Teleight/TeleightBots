package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record RefundStarPayment(
        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "telegram_payment_charge_id", required = true)
        @NotNull
        String telegramPaymentChargeId
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(long userId, String telegramPaymentChargeId) {
        return new RefundStarPayment.Builder().userId(userId).telegramPaymentChargeId(telegramPaymentChargeId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "refundStarPayment";
    }

}
