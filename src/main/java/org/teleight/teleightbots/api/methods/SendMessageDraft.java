package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.MessageEntity;
import org.teleight.teleightbots.api.objects.ParseMode;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SendMessageDraft(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id")
        int messageThreadId,

        @JsonProperty(value = "draft_id", required = true)
        int draftId,

        @JsonProperty(value = "text", required = true)
        @NotNull
        String text,

        @JsonProperty(value = "parse_mode")
        @Nullable
        ParseMode parseMode,

        @JsonProperty(value = "entities")
        @Nullable
        MessageEntity[] entities
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String chatId, int draftId, String text) {
        return new SendMessageDraft.Builder()
                .chatId(chatId)
                .draftId(draftId)
                .text(text);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendMessageDraft";
    }

}
