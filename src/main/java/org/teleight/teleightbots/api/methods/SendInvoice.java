package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodMessage;
import org.teleight.teleightbots.api.objects.InlineKeyboardMarkup;
import org.teleight.teleightbots.api.objects.LabeledPrice;
import org.teleight.teleightbots.api.objects.ReplyParameters;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SendInvoice(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id")
        int messageThreadId,

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
        String providerToken,

        @JsonProperty(value = "currency", required = true)
        @NotNull
        String currency,

        @JsonProperty(value = "prices", required = true)
        @NotNull
        LabeledPrice[] prices,

        @JsonProperty(value = "max_tip_amount")
        int maxTipAmount,

        @JsonProperty(value = "suggested_tip_amounts")
        int[] suggestedTipAmounts,

        @JsonProperty(value = "start_parameter")
        String startParameter,

        @JsonProperty(value = "provider_data")
        String providerData,

        @JsonProperty(value = "photo_url")
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
        boolean isFlexible,

        @JsonProperty(value = "disable_notification")
        boolean disableNotification,

        @JsonProperty(value = "protect_content")
        boolean protectContent,

        @JsonProperty(value = "allow_paid_broadcast")
        boolean allowPaidBroadcast,

        @JsonProperty(value = "message_effect_id")
        String messageEffectId,

        @JsonProperty(value = "reply_parameters")
        ReplyParameters replyParameters,

        @JsonProperty(value = "reply_markup")
        InlineKeyboardMarkup replyMarkup
) implements ApiMethodMessage {

    public static @NotNull Builder ofBuilder(String chatId, String title, String description, String payload, String currency, LabeledPrice[] prices) {
        return new SendInvoice.Builder().chatId(chatId).title(title).description(description).payload(payload).currency(currency).prices(prices);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendInvoice";
    }

}
