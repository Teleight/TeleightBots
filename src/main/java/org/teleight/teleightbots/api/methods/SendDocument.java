package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.MultiPartApiMethodMessage;
import org.teleight.teleightbots.api.objects.InputFile;
import org.teleight.teleightbots.api.objects.MessageEntity;
import org.teleight.teleightbots.api.objects.ParseMode;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;
import org.teleight.teleightbots.api.objects.ReplyParameters;

import java.util.HashMap;
import java.util.Map;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SendDocument(
        @JsonProperty(value = "business_connection_id")
        @Nullable
        String businessConnectionId,

        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id")
        int messageThreadId,

        @JsonProperty(value = "document", required = true)
        @NotNull
        InputFile document,

        @JsonProperty(value = "thumbnail")
        @Nullable
        InputFile thumbnail,

        @JsonProperty(value = "caption")
        @Nullable
        String caption,

        @JsonProperty(value = "parse_mode")
        @Nullable
        ParseMode parseMode,

        @JsonProperty(value = "caption_entities")
        @Nullable
        MessageEntity[] captionEntities,

        @JsonProperty(value = "disable_content_type_detection")
        boolean disableContentTypeDetection,

        @JsonProperty(value = "disable_notification")
        boolean disableNotification,

        @JsonProperty(value = "protect_content")
        boolean protectContent,

        @JsonProperty(value = "message_effect_id")
        String messageEffectId,

        @JsonProperty(value = "reply_parameters")
        @Nullable
        ReplyParameters replyParameters,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements MultiPartApiMethodMessage {

    public static @NotNull Builder ofBuilder(String chatId, InputFile document) {
        return new SendDocument.Builder().chatId(chatId).document(document);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendDocument";
    }

    @Override
    public Map<String, Object> getParameters() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("business_connection_id", businessConnectionId);
        parameters.put("chat_id", chatId);
        parameters.put("message_thread_id", messageThreadId);
        parameters.put("caption", caption);
        parameters.put("parse_mode", parseMode);
        parameters.put("caption_entities", captionEntities);
        parameters.put("disable_content_type_detection", disableContentTypeDetection);
        parameters.put("disable_notification", disableNotification);
        parameters.put("protect_content", protectContent);
        parameters.put("message_effect_id", messageEffectId);
        parameters.put("reply_parameters", replyParameters);
        parameters.put("reply_markup", replyMarkup);
        return parameters;
    }

    @Override
    public Map<String, InputFile> getInputFiles() {
        final Map<String, InputFile> files = new HashMap<>();
        files.put("document", document);
        files.put("thumbnail", thumbnail);
        return files;
    }

}
