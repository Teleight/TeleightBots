package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.Dice;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;
import org.teleight.teleightbots.api.objects.ReplyParameters;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public record SendContact(
        @JsonProperty("business_connection_id")
        @Nullable
        String businessConnectionId,

        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty("message_thread_id")
        int messageThreadId,

        @JsonProperty(value = "phone_number", required = true)
        int phoneNumber,

        @JsonProperty(value = "first_name", required = true)
        String firstName,

        @JsonProperty("last_name")
        @Nullable
        String lastName,

        @JsonProperty("vcard")
        @Nullable
        String vcard,

        @JsonProperty("disable_notification")
        boolean disableNotification,

        @JsonProperty("protect_content")
        boolean protectContent,

        @JsonProperty("message_effect_id")
        @Nullable
        String messageEffectId,

        @JsonProperty("reply_parameters")
        @Nullable
        ReplyParameters replyParameters,

        @JsonProperty("reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements ApiMethod<Dice> {

    public static Builder ofBuilder(String chatId, int phoneNumber, String firstName) {
        return new SendContact.Builder(chatId, phoneNumber, firstName);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendDice";
    }

    @Override
    public @NotNull Dice deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Dice.class);
    }

    public static class Builder {
        private String businessConnectionId;
        private final String chatId;
        private int messageThreadId;
        private final int phoneNumber;
        private final String firstName;
        private String lastName;
        private String vcard;
        private boolean disableNotification;
        private boolean protectContent;
        private String messageEffectId;
        private ReplyParameters replyParameters;
        private ReplyKeyboard replyMarkup;

        Builder(String chatId, int phoneNumber, String firstName) {
            this.chatId = chatId;
            this.phoneNumber = phoneNumber;
            this.firstName = firstName;
        }

        public Builder businessConnectionId(String businessConnectionId) {
            this.businessConnectionId = businessConnectionId;
            return this;
        }

        public Builder messageThreadId(int messageThreadId) {
            this.messageThreadId = messageThreadId;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder vcard(String vcard) {
            this.vcard = vcard;
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

        public Builder replyMarkup(ReplyKeyboard replyMarkup) {
            this.replyMarkup = replyMarkup;
            return this;
        }

        public SendContact build() {
            return new SendContact(this.businessConnectionId, this.chatId, this.messageThreadId, this.phoneNumber, this.firstName, this.lastName, this.vcard, this.disableNotification, this.protectContent, this.messageEffectId, this.replyParameters, this.replyMarkup);
        }
    }
}
