package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.MultiPartApiMethodMessage;
import org.teleight.teleightbots.api.objects.InputPaidMedia;
import org.teleight.teleightbots.api.objects.MessageEntity;
import org.teleight.teleightbots.api.objects.ParseMode;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;
import org.teleight.teleightbots.api.objects.ReplyParameters;

import java.util.HashMap;
import java.util.Map;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SendPaidMedia(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "star_count", required = true)
        int starCount,

        @JsonProperty(value = "media", required = true)
        @NotNull
        InputPaidMedia[] media,

        @JsonProperty(value = "caption")
        @Nullable
        String caption,

        @JsonProperty(value = "parse_mode")
        @Nullable
        ParseMode parseMode,

        @JsonProperty(value = "caption_entities")
        @Nullable
        MessageEntity[] captionEntities,

        @JsonProperty("show_caption_above_media")
        boolean showCaptionAboveMedia,

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
) implements MultiPartApiMethodMessage {

    public static @NotNull Builder ofBuilder(String chatId, int starCount, InputPaidMedia[] media) {
        return new Builder().chatId(chatId).starCount(starCount).media(media);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendPaidMedia";
    }

    @Override
    public @NotNull Map<String, Object> getParameters() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("chat_id", chatId);
        parameters.put("star_count", starCount);
        parameters.put("media", media);
        parameters.put("caption", caption);
        parameters.put("parse_mode", parseMode);
        parameters.put("caption_entities", captionEntities);
        parameters.put("show_caption_above_media", showCaptionAboveMedia);
        parameters.put("disable_notification", disableNotification);
        parameters.put("protect_content", protectContent);
        parameters.put("reply_parameters", replyParameters);
        parameters.put("reply_markup", replyMarkup);
        return parameters;
    }

}
