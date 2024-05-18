package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;
import org.teleight.teleightbots.api.objects.ReplyParameters;
import org.teleight.teleightbots.api.objects.Venue;

public record SendVenue(
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

        @JsonProperty(value = "title", required = true)
        @NotNull
        String title,

        @JsonProperty(value = "address", required = true)
        @NotNull
        String address,

        @JsonProperty(value = "foursquare_id")
        @Nullable
        String foursquareId,

        @JsonProperty(value = "foursquare_type")
        @Nullable
        String foursquareType,

        @JsonProperty(value = "google_place_id")
        @Nullable
        String googlePlaceId,

        @JsonProperty(value = "google_place_type")
        @Nullable
        String googlePlaceType,

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
) implements ApiMethod<Venue> {

    public static Builder ofBuilder(String chatId, float latitude, float longitude, String title, String address) {
        return new SendVenue.Builder(chatId, latitude, longitude, title, address);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendVenue";
    }

    public static class Builder {
        private String businessConnectionId;
        private final String chatId;
        private int messageThreadId;
        private final float latitude;
        private final float longitude;
        private final String title;
        private final String address;
        private String foursquareId;
        private String foursquareType;
        private String googlePlaceId;
        private String googlePlaceType;
        private boolean disableNotification;
        private boolean protectContent;
        private ReplyParameters replyParameters;
        private ReplyKeyboard replyMarkup;

        Builder(String chatId, float latitude, float longitude, String title, String address) {
            this.chatId = chatId;
            this.latitude = latitude;
            this.longitude = longitude;
            this.title = title;
            this.address = address;
        }

        public Builder businessConnectionId(String businessConnectionId) {
            this.businessConnectionId = businessConnectionId;
            return this;
        }

        public Builder messageThreadId(int messageThreadId) {
            this.messageThreadId = messageThreadId;
            return this;
        }

        public Builder foursquareId(String foursquareId) {
            this.foursquareId = foursquareId;
            return this;
        }

        public Builder foursquareType(String foursquareType) {
            this.foursquareType = foursquareType;
            return this;
        }

        public Builder googlePlaceId(String googlePlaceId) {
            this.googlePlaceId = googlePlaceId;
            return this;
        }

        public Builder googlePlaceType(String googlePlaceType) {
            this.googlePlaceType = googlePlaceType;
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

        public SendVenue build() {
            return new SendVenue(this.businessConnectionId, this.chatId, this.messageThreadId, this.latitude, this.longitude, this.title, this.address, this.foursquareId, this.foursquareType, this.googlePlaceId, this.googlePlaceType, this.disableNotification, this.protectContent, this.replyParameters, this.replyMarkup);
        }
    }
}
