package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.chat.Chat;
import org.teleight.teleightbots.api.objects.giveaway.Giveaway;
import org.teleight.teleightbots.api.objects.giveaway.GiveawayWinners;
import org.teleight.teleightbots.api.objects.origin.MessageOrigin;
import org.teleight.teleightbots.api.objects.payment.Invoice;
import org.teleight.teleightbots.api.objects.poll.Poll;

public record ExternalReplyInfo(
        @JsonProperty(value = "origin", required = true)
        @NotNull
        MessageOrigin origin,

        @JsonProperty(value = "chat")
        @Nullable
        Chat chat,

        @JsonProperty(value = "message_id")
        @Nullable
        Integer messageId,

        @JsonProperty(value = "link_preview_options")
        @Nullable
        LinkPreviewOptions linkPreviewOptions,

        @JsonProperty(value = "animation")
        @Nullable
        Animation animation,

        @JsonProperty(value = "audio")
        @Nullable
        Audio audio,

        @JsonProperty(value = "document")
        @Nullable
        Document document,

        @JsonProperty(value = "photo")
        @Nullable
        PhotoSize[] photo,

        @JsonProperty(value = "sticker")
        @Nullable
        Sticker sticker,

        @JsonProperty(value = "story")
        @Nullable
        Story story,

        @JsonProperty(value = "video")
        @Nullable
        Video video,

        @JsonProperty(value = "video_note")
        @Nullable
        VideoNote videoNote,

        @JsonProperty(value = "voice")
        @Nullable
        Voice voice,

        @JsonProperty(value = "has_media_spoiler")
        @Nullable
        Boolean hasMediaSpoiler,

        @JsonProperty(value = "contact")
        @Nullable
        Contact contact,

        @JsonProperty(value = "dice")
        @Nullable
        Dice dice,

        @JsonProperty(value = "game")
        @Nullable
        Game game,

        @JsonProperty(value = "giveaway")
        @Nullable
        Giveaway giveaway,

        @JsonProperty(value = "giveaway_winners")
        @Nullable
        GiveawayWinners giveawayWinners,

        @JsonProperty(value = "invoice")
        @Nullable
        Invoice invoice,

        @JsonProperty(value = "location")
        @Nullable
        Location location,

        @JsonProperty(value = "poll")
        @Nullable
        Poll poll,

        @JsonProperty(value = "venue")
        @Nullable
        Venue venue
) implements ApiResult {
}
