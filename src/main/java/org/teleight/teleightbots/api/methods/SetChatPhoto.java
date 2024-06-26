package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.MultiPartApiMethodBoolean;
import org.teleight.teleightbots.api.objects.InputFile;

import java.util.HashMap;
import java.util.Map;

public record SetChatPhoto(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "photo", required = true)
        @NotNull
        InputFile photo
) implements MultiPartApiMethodBoolean {

    public static Builder ofBuilder(String chatId, InputFile photo) {
        return new SetChatPhoto.Builder(chatId, photo);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setChatPhoto";
    }

    @Override
    public Map<String, Object> getParameters() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("chat_id", chatId);
        return parameters;
    }

    @Override
    public Map<String, InputFile> getInputFiles() {
        final Map<String, InputFile> files = new HashMap<>();
        files.put("photo", photo);
        return files;
    }

    public static class Builder {
        private final String chatId;
        private final InputFile photo;

        Builder(String chatId, InputFile photo) {
            this.chatId = chatId;
            this.photo = photo;
        }

        public SetChatPhoto build() {
            return new SetChatPhoto(this.chatId, this.photo);
        }
    }
}
