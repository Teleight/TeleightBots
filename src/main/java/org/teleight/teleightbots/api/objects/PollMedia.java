package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record PollMedia(
        @JsonProperty(value = "animation")
        @Nullable
        Animation animation,

        @JsonProperty(value = "audio")
        @Nullable
        Audio audio,

        @JsonProperty(value = "document")
        @Nullable
        Document document,

        @JsonProperty(value = "live_photo")
        @Nullable
        LivePhoto livePhoto,

        @JsonProperty(value = "location")
        @Nullable
        Location location,

        @JsonProperty(value = "photo")
        @Nullable
        PhotoSize[] photo,

        @JsonProperty(value = "sticker")
        @Nullable
        Sticker sticker,

        @JsonProperty(value = "venue")
        @Nullable
        Venue venue,

        @JsonProperty(value = "video")
        @Nullable
        Video video
) implements ApiResult {

    public static @NotNull Builder ofBuilder() {
        return new PollMedia.Builder();
    }

}
