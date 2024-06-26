package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.Location;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;
import org.teleight.teleightbots.api.objects.ReplyParameters;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public record SendLocation(
        @JsonProperty(value = "business_connection_id")
        @Nullable
        String businessConnectionId,

        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id")
        int messageThreadId,

        @JsonProperty(value = "latitude", required = true)
        float latitude,

        @JsonProperty(value = "longitude", required = true)
        float longitude,

        @JsonProperty(value = "horizontal_accuracy")
        float horizontalAccuracy,

        @JsonProperty(value = "live_period")
        int livePeriod,

        @JsonProperty(value = "heading")
        int heading,

        @JsonProperty(value = "proximity_alert_radius")
        int proximityAlertRadius,

        @JsonProperty(value = "disable_notification")
        boolean disableNotification,

        @JsonProperty(value = "protect_content")
        boolean protectContent,

        @JsonProperty(value = "reply_parameters")
        @Nullable
        ReplyParameters replyParameters,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements ApiMethod<Location> {

    public static Builder ofBuilder(String chatId, float latitude, float longitude) {
        return new SendLocation.Builder(chatId, latitude, longitude);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendLocation";
    }

    @Override
    public @NotNull Location deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Location.class);
    }

    public static class Builder {
        private String businessConnectionId;
        private final String chatId;
        private int messageThreadId;
        private final Float latitude;
        private final Float longitude;
        private Float horizontalAccuracy;
        private int livePeriod;
        private int heading;
        private int proximityAlertRadius;
        private boolean disableNotification;
        private boolean protectContent;
        private ReplyParameters replyParameters;
        private ReplyKeyboard replyMarkup;

        Builder(String chatId, Float latitude, Float longitude) {
            this.chatId = chatId;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public Builder businessConnectionId(String businessConnectionId) {
            this.businessConnectionId = businessConnectionId;
            return this;
        }

        public Builder messageThreadId(int messageThreadId) {
            this.messageThreadId = messageThreadId;
            return this;
        }

        public Builder horizontalAccuracy(Float horizontalAccuracy) {
            this.horizontalAccuracy = horizontalAccuracy;
            return this;
        }

        public Builder livePeriod(int livePeriod) {
            this.livePeriod = livePeriod;
            return this;
        }

        public Builder heading(int heading) {
            this.heading = heading;
            return this;
        }

        public Builder proximityAlertRadius(int proximityAlertRadius) {
            this.proximityAlertRadius = proximityAlertRadius;
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

        public Builder replyParameters(ReplyParameters replyParameters) {
            this.replyParameters = replyParameters;
            return this;
        }

        public Builder replyMarkup(ReplyKeyboard replyMarkup) {
            this.replyMarkup = replyMarkup;
            return this;
        }

        public SendLocation build() {
            return new SendLocation(this.businessConnectionId, this.chatId, this.messageThreadId, this.latitude, this.longitude, this.horizontalAccuracy, this.livePeriod, this.heading, this.proximityAlertRadius, this.disableNotification, this.protectContent, this.replyParameters, this.replyMarkup);
        }
    }
}
