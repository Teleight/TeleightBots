package org.teleight.teleightbots.api.methods.send;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.Location;
import org.teleight.teleightbots.api.objects.ReplyParameters;
import org.teleight.teleightbots.api.objects.keyboard.ReplyKeyboard;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder
public record SendLocation(
        @JsonProperty(value = "business_connection_id")
        @Nullable
        String businessConnectionId,

        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id")
        @Nullable
        Integer messageThreadId,

        @JsonProperty(value = "latitude", required = true)
        @NotNull
        Float latitude,

        @JsonProperty(value = "longitude", required = true)
        @NotNull
        Float longitude,

        @JsonProperty(value = "horizontal_accuracy")
        @Nullable
        Float horizontalAccuracy,

        @JsonProperty(value = "live_period")
        @Nullable
        Integer livePeriod,

        @JsonProperty(value = "heading")
        @Nullable
        Integer heading,

        @JsonProperty(value = "proximity_alert_radius")
        @Nullable
        Integer proximityAlertRadius,

        @JsonProperty(value = "disable_notification")
        @Nullable
        Boolean disableNotification,

        @JsonProperty(value = "protect_content")
        @Nullable
        Boolean protectContent,

        @JsonProperty(value = "reply_parameters")
        @Nullable
        ReplyParameters replyParameters,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements ApiMethod<Location> {
    @Override
    public @NotNull Location deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Location.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendLocation";
    }

    public static class SendLocationBuilder {
        @Tolerate
        public SendLocationBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
