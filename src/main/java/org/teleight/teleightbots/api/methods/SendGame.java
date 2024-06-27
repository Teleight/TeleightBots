package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodMessage;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;
import org.teleight.teleightbots.api.objects.ReplyParameters;

public record SendGame(
        @JsonProperty("business_connection_id")
        @Nullable
        String businessConnectionId,

        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty("message_thread_id")
        int messageThreadId,

        @JsonProperty(value = "game_short_name", required = true)
        @NotNull
        String gameShortName,

        @JsonProperty("disable_notification")
        boolean disableNotification,

        @JsonProperty("protect_content")
        boolean protectContent,

        @JsonProperty("message_effect_id")
        String messageEffectId,

        @JsonProperty("reply_parameters")
        @Nullable
        ReplyParameters replyParameters,

        @JsonProperty("reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements ApiMethodMessage {

    public static SendGame.Builder ofBuilder(String chatId, String gameShortName) {
        return new SendGame.Builder(chatId, gameShortName);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendGame";
    }

    public static class Builder {
        private String businessConnectionId;
        private final String chatId;
        private int messageThreadId;
        private final String gameShortName;
        private boolean disableNotification;
        private boolean protectContent;
        private String messageEffectId;
        private ReplyParameters replyParameters;
        private ReplyKeyboard replyMarkup;

        Builder(String chatId, String gameShortName) {
            this.chatId = chatId;
            this.gameShortName = gameShortName;
        }

        public Builder businessConnectionId(String businessConnectionId) {
            this.businessConnectionId = businessConnectionId;
            return this;
        }

        public Builder messageThreadId(int messageThreadId) {
            this.messageThreadId = messageThreadId;
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

        public SendGame build() {
            return new SendGame(this.businessConnectionId, this.chatId, this.messageThreadId, this.gameShortName, this.disableNotification, this.protectContent, this.messageEffectId, this.replyParameters, this.replyMarkup);
        }
    }

}
