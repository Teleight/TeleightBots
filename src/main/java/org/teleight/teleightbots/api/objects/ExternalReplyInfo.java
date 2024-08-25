package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record ExternalReplyInfo(
        @JsonProperty(value = "origin", required = true)
        @NotNull
        MessageOrigin origin,

        @JsonProperty("chat")
        @Nullable
        Chat chat,

        @JsonProperty("message_id")
        int messageId,

        @JsonProperty("link_preview_options")
        @Nullable
        LinkPreviewOptions linkPreviewOptions,

        @JsonProperty("animation")
        @Nullable
        Animation animation,

        @JsonProperty("audio")
        @Nullable
        Audio audio,

        @JsonProperty("document")
        @Nullable
        Document document,

        @JsonProperty("paid_media")
        @Nullable
        PaidMedia paidMedia,

        @JsonProperty("photo")
        @Nullable
        PhotoSize[] photo,

        @JsonProperty("sticker")
        @Nullable
        Sticker sticker,

        @JsonProperty("story")
        @Nullable
        Story story,

        @JsonProperty("video")
        @Nullable
        Video video,

        @JsonProperty("video_note")
        @Nullable
        VideoNote videoNote,

        @JsonProperty("voice")
        @Nullable
        Voice voice,

        @JsonProperty("has_media_spoiler")
        boolean hasMediaSpoiler,

        @JsonProperty("contact")
        @Nullable
        Contact contact,

        @JsonProperty("dice")
        @Nullable
        Dice dice,

        @JsonProperty("game")
        @Nullable
        Game game,

        @JsonProperty("giveaway")
        @Nullable
        Giveaway giveaway,

        @JsonProperty("giveaway_winners")
        @Nullable
        GiveawayWinners giveawayWinners,

        @JsonProperty("invoice")
        @Nullable
        Invoice invoice,

        @JsonProperty("location")
        @Nullable
        Location location,

        @JsonProperty("poll")
        @Nullable
        Poll poll,

        @JsonProperty("venue")
        @Nullable
        Venue venue
) implements ApiResult {
}
