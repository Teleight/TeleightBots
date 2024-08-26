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
        @JsonProperty(value = "title", required = true)
        @NotNull
        String title,

        @JsonProperty(value = "description", required = true)
        @NotNull
        String description,

        @JsonProperty(value = "payload", required = true)
        @NotNull
        String payload,

        @JsonProperty("provider_token")
        @NotNull
        String providerToken,

        @JsonProperty(value = "currency", required = true)
        @NotNull
        String currency,

        @JsonProperty(value = "prices", required = true)
        @NotNull
        LabeledPrice[] prices,

        @JsonProperty("max_tip_amount")
        int maxTipAmount,

        @JsonProperty("suggested_tip_amounts")
        int[] suggestedTipAmounts,

        @JsonProperty("provider_data")
        @Nullable
        String providerData,

        @JsonProperty("photo_url")
        @Nullable
        String photoUrl,

        @JsonProperty("photo_size")
        int photoSize,

        @JsonProperty("photo_width")
        int photoWidth,

        @JsonProperty("photo_height")
        int photoHeight,

        @JsonProperty("need_name")
        boolean needName,

        @JsonProperty("need_phone_number")
        boolean needPhoneNumber,

        @JsonProperty("need_email")
        boolean needEmail,

        @JsonProperty("need_shipping_address")
        boolean needShippingAddress,

        @JsonProperty("send_phone_number_to_provider")
        boolean sendPhoneNumberToProvider,

        @JsonProperty("send_email_to_provider")
        boolean sendEmailToProvider,

        @JsonProperty("is_flexible")
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
