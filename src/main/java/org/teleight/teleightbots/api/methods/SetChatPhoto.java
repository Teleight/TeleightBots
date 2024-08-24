package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.MultiPartApiMethodBoolean;
import org.teleight.teleightbots.api.objects.InputFile;

import java.util.HashMap;
import java.util.Map;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetChatPhoto(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "photo", required = true)
        @NotNull
        InputFile photo
) implements MultiPartApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String chatId, InputFile photo) {
        return new SetChatPhoto.Builder().chatId(chatId).photo(photo);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setChatPhoto";
    }

    @Override
    public Map<String, Object> getParameters() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("chat_id", chatId);
        parameters.put("photo", photo);
        return parameters;
    }

}
