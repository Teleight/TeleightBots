package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.Location;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;
import org.teleight.teleightbots.api.objects.ReplyParameters;

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

    public static Builder of(String chatId, float latitude, float longitude) {
        return new SendLocation.Builder(chatId, latitude, longitude);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendLocation";
    }

    public static class Builder {
        private String businessConnectionId;
        private final String chatId;
        private int messageThreadId;
        private final Float latitude;
        private final Float longitude;
        private Float horizontalAccuracy;
        private Integer livePeriod;
        private Integer heading;
        private Integer proximityAlertRadius;
        private Boolean disableNotification;
        private Boolean protectContent;
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

        public Builder livePeriod(Integer livePeriod) {
            this.livePeriod = livePeriod;
            return this;
        }

        public Builder heading(Integer heading) {
            this.heading = heading;
            return this;
        }

        public Builder proximityAlertRadius(Integer proximityAlertRadius) {
            this.proximityAlertRadius = proximityAlertRadius;
            return this;
        }

        public Builder disableNotification(Boolean disableNotification) {
            this.disableNotification = disableNotification;
            return this;
        }

        public Builder protectContent(Boolean protectContent) {
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
