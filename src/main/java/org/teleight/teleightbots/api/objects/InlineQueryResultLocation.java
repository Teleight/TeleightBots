package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record InlineQueryResultLocation(
        @JsonProperty(value = "id", required = true)
        @NotNull
        String id,

        @JsonProperty(value = "latitude", required = true)
        float latitude,

        @JsonProperty(value = "longitude", required = true)
        float longitude,

        @JsonProperty(value = "title", required = true)
        @NotNull
        String title,

        @JsonProperty(value = "horizontal_accuracy")
        @Nullable
        Float horizontalAccuracy,

        @JsonProperty(value = "live_period")
        int livePeriod,

        @JsonProperty(value = "heading")
        int heading,

        @JsonProperty(value = "proximity_alert_radius")
        int proximityAlertRadius,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup,

        @JsonProperty(value = "input_message_content")
        @Nullable
        InputMessageContent inputMessageContent,

        @JsonProperty(value = "thumbnail_url")
        @Nullable
        String thumbnailUrl,

        @JsonProperty(value = "thumbnail_width")
        int thumbnailWidth,

        @JsonProperty(value = "thumbnail_height")
        int thumbnailHeight
) implements InlineQueryResult {

    @Override
    public InlineQueryResultType type() {
        return InlineQueryResultType.INLINE_QUERY_RESULT_LOCATION;
    }

}
