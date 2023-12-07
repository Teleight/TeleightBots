package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.MultiPartApiMethod;
import org.teleight.teleightbots.api.objects.InputFile;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.utils.ParseMode;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;
import org.teleight.teleightbots.utils.MultiPartBodyPublisher;

public record SendPhoto(
        @JsonProperty(value = "chat_id",required = true)
        String chatId,

        @JsonProperty(value = "photo",required = true)
        InputFile photo,

        @JsonProperty(value = "caption")
        @Nullable
        String caption,

        @JsonProperty("parse_mode")
        @Nullable
        ParseMode parseMode
) implements MultiPartApiMethod<Message> {

    public static @NotNull Builder builder() {
        return new BuilderImpl();
    }

    @Override
    public @NotNull Message deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Message.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendPhoto";
    }

    @Override
    public void buildRequest(MultiPartBodyPublisher bodyCreator) {
        bodyCreator.addPart("photo", photo.file(), photo.fileName());
        bodyCreator.addPart("chat_id", chatId);

        if (caption != null) {
            bodyCreator.addPart("caption", caption);
            if(parseMode != null){
                bodyCreator.addPart("parse_mode", parseMode.getFieldValue());
            }
        }
    }

    public sealed interface Builder permits BuilderImpl {
        @NotNull Builder chatId(@NotNull String chatId);

        default @NotNull Builder chatId(long chatId){
            return chatId("" + chatId);
        }

        @NotNull Builder photo(@NotNull InputFile photo);

        @NotNull Builder caption(@NotNull String caption);

        @NotNull Builder parseMode(@NotNull ParseMode parseMode);

        @NotNull SendPhoto build();
    }

    static final class BuilderImpl implements Builder {
        private String chatId;
        private InputFile photo;
        private String caption;
        private ParseMode parseMode;

        @Override
        public @NotNull Builder chatId(@NotNull String chatId) {
            this.chatId = chatId;
            return this;
        }

        @Override
        public @NotNull Builder photo(@NotNull InputFile photo) {
            this.photo = photo;
            return this;
        }

        @Override
        public @NotNull Builder caption(@NotNull String caption) {
            this.caption = caption;
            return this;
        }

        @Override
        public @NotNull Builder parseMode(@NotNull ParseMode parseMode) {
            this.parseMode = parseMode;
            return this;
        }

        @Override
        public @NotNull SendPhoto build() {
            return new SendPhoto(chatId, photo, caption, parseMode);
        }
    }

}
