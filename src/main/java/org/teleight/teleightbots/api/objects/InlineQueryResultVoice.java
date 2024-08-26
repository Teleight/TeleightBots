package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record InlineQueryResultVoice(
        @JsonProperty(value = "id", required = true)
        @NotNull
        String id,

        @JsonProperty(value = "voice_url", required = true)
        @NotNull
        String voiceUrl,

        @JsonProperty(value = "title", required = true)
        @NotNull
        String title,

        @JsonProperty(value = "caption")
        @Nullable
        String caption,

        @JsonProperty(value = "parse_mode")
        @Nullable
        ParseMode parseMode,

        @JsonProperty(value = "caption_entities")
        @Nullable
        MessageEntity[] captionEntities,

        @JsonProperty(value = "voice_duration")
        int voiceDuration,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup,

        @JsonProperty(value = "input_message_content")
        @Nullable
        InputMessageContent inputMessageContent
) implements InlineQueryResult {

    public static @NotNull Builder ofBuilder(String id, String voiceUrl, String title) {
        return new InlineQueryResultVoice.Builder().id(id).voiceUrl(voiceUrl).title(title);
    }

    @Override
    public String type() {
        return "voice";
    }

}
