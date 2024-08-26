package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record InlineQueryResultVenue(
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

    public static @NotNull Builder ofBuilder(String id, float latitude, float longitude, String title, String address) {
        return new InlineQueryResultVenue.Builder()
                .id(id)
                .latitude(latitude)
                .longitude(longitude)
                .title(title)
                .address(address);
    }

    @Override
    public String type() {
        return "venue";
    }

}
