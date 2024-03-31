package org.teleight.teleightbots.api.methods.chat.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.MultiPartApiMethodBoolean;
import org.teleight.teleightbots.api.objects.input.InputFile;
import org.teleight.teleightbots.utils.MultiPartBodyPublisher;

@Builder
public record SetChatPhoto(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "photo", required = true)
        @NotNull
        InputFile photo
) implements MultiPartApiMethodBoolean {
    @Override
    public @NotNull String getEndpointURL() {
        return "setChatPhoto";
    }

    @Override
    public void buildRequest(MultiPartBodyPublisher bodyCreator) throws JsonProcessingException {
        bodyCreator.addPart("chat_id", chatId);
        bodyCreator.addPart("video", photo.file(), photo.fileName());
    }

    public static class SetChatPhotoBuilder {
        @Tolerate
        public SetChatPhotoBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
