package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.LabeledPrice;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record CreateInvoiceLink(
        @JsonProperty(value = "business_connection_id")
        @Nullable
        String businessConnectionId,

        @JsonProperty(value = "title", required = true)
        @NotNull
        String title,

        @JsonProperty(value = "description", required = true)
        @NotNull
        String description,

        @JsonProperty(value = "payload", required = true)
        @NotNull
        String payload,

        @JsonProperty(value = "provider_token")
        @NotNull
        String providerToken,

        @JsonProperty(value = "currency", required = true)
        @NotNull
        String currency,

        @JsonProperty(value = "prices", required = true)
        @NotNull
        LabeledPrice[] prices,

        @JsonProperty(value = "subscription_period")
        int subscriptionPeriod,

        @JsonProperty(value = "max_tip_amount")
        int maxTipAmount,

        @JsonProperty(value = "suggested_tip_amounts")
        int[] suggestedTipAmounts,

        @JsonProperty(value = "provider_data")
        @Nullable
        String providerData,

        @JsonProperty(value = "photo_url")
        @Nullable
        String photoUrl,

        @JsonProperty(value = "photo_size")
        int photoSize,

        @JsonProperty(value = "photo_width")
        int photoWidth,

        @JsonProperty(value = "photo_height")
        int photoHeight,

        @JsonProperty(value = "need_name")
        boolean needName,

        @JsonProperty(value = "need_phone_number")
        boolean needPhoneNumber,

        @JsonProperty(value = "need_email")
        boolean needEmail,

        @JsonProperty(value = "need_shipping_address")
        boolean needShippingAddress,

        @JsonProperty(value = "send_phone_number_to_provider")
        boolean sendPhoneNumberToProvider,

        @JsonProperty(value = "send_email_to_provider")
        boolean sendEmailToProvider,

        @JsonProperty(value = "is_flexible")
        boolean isFlexible
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String title, String description, String payload, String currency, LabeledPrice[] prices) {
        return new CreateInvoiceLink.Builder()
                .title(title)
                .description(description)
                .payload(payload)
                .providerToken(currency)
                .prices(prices);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "createInvoiceLink";
    }

}
