package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.MultiPartApiMethodMessage;
import org.teleight.teleightbots.api.objects.InputMedia;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;

import java.util.HashMap;
import java.util.Map;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record EditMessageMedia(
        @JsonProperty(value = "business_connection_id")
        @Nullable
        String businessConnectionId,

        @JsonProperty(value = "chat_id")
        @Nullable
        String chatId,

        @JsonProperty(value = "message_id")
        int messageId,

        @JsonProperty(value = "inline_message_id")
        @Nullable
        String inlineMessageId,

        @JsonProperty(value = "media", required = true)
        @NotNull
        InputMedia media,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements MultiPartApiMethodMessage {

    public static @NotNull Builder ofBuilder(InputMedia media) {
        return new EditMessageMedia.Builder().media(media);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "editMessageMedia";
    }

    @Override
    public @NotNull Map<String, Object> getParameters() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("business_connection_id", businessConnectionId);
        parameters.put("chat_id", chatId);
        parameters.put("message_id", messageId);
        parameters.put("inline_message_id", inlineMessageId);
        parameters.put("media", media);
        parameters.put("reply_markup", replyMarkup);
        return parameters;
    }
}
