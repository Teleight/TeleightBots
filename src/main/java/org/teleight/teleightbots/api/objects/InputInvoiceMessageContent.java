package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record InputInvoiceMessageContent(
        @JsonProperty(value = "title", required = true)
        String title,

        @JsonProperty(value = "description", required = true)
        String description,

        @JsonProperty(value = "payload", required = true)
        String payload,

        @JsonProperty(value = "provider_token", required = true)
        String providerToken,

        @JsonProperty(value = "currency", required = true)
        String currency,

        @JsonProperty(value = "prices", required = true)
        List<LabeledPrice> prices,

        @JsonProperty(value = "max_tip_amount")
        @Nullable
        Integer maxTipAmount,

        @JsonProperty(value = "suggested_tip_amounts")
        @Nullable
        List<Integer> suggestedTipAmounts,

        @JsonProperty(value = "provider_data")
        @Nullable
        String providerData,

        @JsonProperty(value = "photo_url")
        @Nullable
        String photoUrl,

        @JsonProperty(value = "photo_size")
        @Nullable
        Integer photoSize,

        @JsonProperty(value = "photo_width")
        @Nullable
        Integer photoWidth,

        @JsonProperty(value = "photo_height")
        @Nullable
        Integer photoHeight,

        @JsonProperty(value = "need_name")
        @Nullable
        Boolean needName,

        @JsonProperty(value = "need_phone_number")
        @Nullable
        Boolean needPhoneNumber,

        @JsonProperty(value = "need_email")
        @Nullable
        Boolean needEmail,

        @JsonProperty(value = "need_shipping_address")
        @Nullable
        Boolean needShippingAddress,

        @JsonProperty(value = "send_phone_number_to_provider")
        @Nullable
        Boolean sendPhoneNumberToProvider,

        @JsonProperty(value = "send_email_to_provider")
        @Nullable
        Boolean sendEmailToProvider,

        @JsonProperty(value = "is_flexible")
        @Nullable
        Boolean isFlexible
) implements InputMessageContent {
}
