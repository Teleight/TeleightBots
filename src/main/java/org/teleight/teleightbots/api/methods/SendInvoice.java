package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodMessage;
import org.teleight.teleightbots.api.objects.InlineKeyboardMarkup;
import org.teleight.teleightbots.api.objects.LabeledPrice;
import org.teleight.teleightbots.api.objects.ReplyParameters;

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

        @JsonProperty(value = "message_effect_id")
        String messageEffectId,

        @JsonProperty(value = "reply_parameters")
        ReplyParameters replyParameters,

        @JsonProperty(value = "reply_markup")
        InlineKeyboardMarkup replyMarkup
) implements ApiMethodMessage {

    public static SendInvoice.Builder ofBuilder(String chatId, String title, String description, String payload, String currency, LabeledPrice[] prices) {
        return new SendInvoice.Builder(chatId, title, description, payload, currency, prices);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendInvoice";
    }

    public static class Builder {
        private final String chatId;
        private final String title;
        private final String description;
        private final String payload;
        private final String currency;
        private final LabeledPrice[] prices;
        private int messageThreadId;
        private String providerToken;
        private int maxTipAmount;
        private int[] suggestedTipAmounts;
        private String startParameter;
        private String providerData;
        private String photoUrl;
        private int photoSize;
        private int photoWidth;
        private int photoHeight;
        private boolean needName;
        private boolean needPhoneNumber;
        private boolean needEmail;
        private boolean needShippingAddress;
        private boolean sendPhoneNumberToProvider;
        private boolean sendEmailToProvider;
        private boolean isFlexible;
        private boolean disableNotification;
        private boolean protectContent;
        private String messageEffectId;
        private ReplyParameters replyParameters;
        private InlineKeyboardMarkup replyMarkup;

        Builder(String chatId, String title, String description, String payload, String currency, LabeledPrice[] prices) {
            this.chatId = chatId;
            this.title = title;
            this.description = description;
            this.payload = payload;
            this.currency = currency;
            this.prices = prices;
        }

        public Builder messageThreadId(Integer messageThreadId) {
            this.messageThreadId = messageThreadId;
            return this;
        }

        public Builder providerToken(String providerToken) {
            this.providerToken = providerToken;
            return this;
        }

        public Builder maxTipAmount(Integer maxTipAmount) {
            this.maxTipAmount = maxTipAmount;
            return this;
        }

        public Builder suggestedTipAmounts(int[] suggestedTipAmounts) {
            this.suggestedTipAmounts = suggestedTipAmounts;
            return this;
        }

        public Builder startParameter(String startParameter) {
            this.startParameter = startParameter;
            return this;
        }

        public Builder providerData(String providerData) {
            this.providerData = providerData;
            return this;
        }

        public Builder photoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
            return this;
        }

        public Builder photoSize(int photoSize) {
            this.photoSize = photoSize;
            return this;
        }

        public Builder photoWidth(int photoWidth) {
            this.photoWidth = photoWidth;
            return this;
        }

        public Builder photoHeight(int photoHeight) {
            this.photoHeight = photoHeight;
            return this;
        }

        public Builder needName(boolean needName) {
            this.needName = needName;
            return this;
        }

        public Builder needPhoneNumber(boolean needPhoneNumber) {
            this.needPhoneNumber = needPhoneNumber;
            return this;
        }

        public Builder needEmail(boolean needEmail) {
            this.needEmail = needEmail;
            return this;
        }

        public Builder needShippingAddress(boolean needShippingAddress) {
            this.needShippingAddress = needShippingAddress;
            return this;
        }

        public Builder sendPhoneNumberToProvider(boolean sendPhoneNumberToProvider) {
            this.sendPhoneNumberToProvider = sendPhoneNumberToProvider;
            return this;
        }

        public Builder sendEmailToProvider(boolean sendEmailToProvider) {
            this.sendEmailToProvider = sendEmailToProvider;
            return this;
        }

        public Builder isFlexible(boolean isFlexible) {
            this.isFlexible = isFlexible;
            return this;
        }

        public Builder disableNotification(boolean disableNotification) {
            this.disableNotification = disableNotification;
            return this;
        }

        public Builder protectContent(boolean protectContent) {
            this.protectContent = protectContent;
            return this;
        }

        public Builder messageEffectId(String messageEffectId) {
            this.messageEffectId = messageEffectId;
            return this;
        }

        public Builder replyParameters(ReplyParameters replyParameters) {
            this.replyParameters = replyParameters;
            return this;
        }

        public Builder replyMarkup(InlineKeyboardMarkup replyMarkup) {
            this.replyMarkup = replyMarkup;
            return this;
        }

        public SendInvoice build() {
            return new SendInvoice(this.chatId, this.messageThreadId, this.title, this.description, this.payload,
                    this.providerToken, this.currency, this.prices, this.maxTipAmount, this.suggestedTipAmounts,
                    this.startParameter, this.providerData, this.photoUrl, this.photoSize, this.photoWidth, this.photoHeight, this.needName,
                    this.needPhoneNumber, this.needEmail, this.needShippingAddress, this.sendPhoneNumberToProvider, this.sendEmailToProvider, this.isFlexible,
                    this.disableNotification, this.protectContent, this.messageEffectId, this.replyParameters, this.replyMarkup);
        }
    }

}
