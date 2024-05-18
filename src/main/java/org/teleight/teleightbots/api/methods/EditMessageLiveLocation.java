package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodSerializable;
import org.teleight.teleightbots.api.objects.InlineKeyboardMarkup;
import org.teleight.teleightbots.api.objects.Message;

import java.io.Serializable;
import java.util.List;

public record EditMessageLiveLocation(
        @JsonProperty(value = "chat_id")
        @Nullable
        String chatId,

        @JsonProperty(value = "message_id")
        int messageId,

        @JsonProperty(value = "inline_message_id")
        @Nullable
        String inlineMessageId,

        @JsonProperty(value = "latitude", required = true)
        float latitude,

        @JsonProperty(value = "longitude", required = true)
        float longitude,

        @JsonProperty(value = "live_period")
        int livePeriod,

        @JsonProperty(value = "horizontal_accuracy")
        float horizontalAccuracy,

        @JsonProperty(value = "heading")
        int heading,

        @JsonProperty(value = "proximity_alert_radius")
        int proximityAlertRadius,

        @JsonProperty(value = "reply_markup")
        @Nullable
        InlineKeyboardMarkup replyMarkup
) implements ApiMethodSerializable {

    public static Builder of(float latitude, float longitude) {
        return new EditMessageLiveLocation.Builder(latitude, longitude);
    }

    @Override
    public List<Class<? extends Serializable>> getSerializableClasses() {
        return List.of(Message.class, Boolean.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "editMessageLiveLocation";
    }

    public static class Builder {
        private String chatId;
        private int messageId;
        private String inlineMessageId;
        private final float latitude;
        private final float longitude;
        private int livePeriod;
        private float horizontalAccuracy;
        private int heading;
        private int proximityAlertRadius;
        private InlineKeyboardMarkup replyMarkup;

        Builder(float latitude, float longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public Builder chatId(String chatId) {
            this.chatId = chatId;
            return this;
        }

        public Builder messageId(int messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder inlineMessageId(String inlineMessageId) {
            this.inlineMessageId = inlineMessageId;
            return this;
        }

        public Builder livePeriod(int livePeriod) {
            this.livePeriod = livePeriod;
            return this;
        }

        public Builder horizontalAccuracy(float horizontalAccuracy) {
            this.horizontalAccuracy = horizontalAccuracy;
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

        public Builder replyMarkup(InlineKeyboardMarkup replyMarkup) {
            this.replyMarkup = replyMarkup;
            return this;
        }

        public EditMessageLiveLocation build() {
            return new EditMessageLiveLocation(this.chatId, this.messageId, this.inlineMessageId, this.latitude, this.longitude, this.livePeriod, this.horizontalAccuracy, this.heading, this.proximityAlertRadius, this.replyMarkup);
        }

    }
}
