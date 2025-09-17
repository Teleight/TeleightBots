package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record ReplyParameters(
        @JsonProperty(value = "message_id", required = true)
        int messageId,

        @JsonProperty(value = "chat_id")
        @Nullable
        String chatId,

        @JsonProperty(value = "allow_sending_without_reply")
        boolean allowSendingWithoutReply,

        @JsonProperty(value = "quote")
        @Nullable
        String quote,

        @JsonProperty(value = "quote_parse_mode")
        @Nullable
        ParseMode quoteParseMode,

        @JsonProperty(value = "quote_entities")
        @Nullable
        MessageEntity[] quoteEntities,

        @JsonProperty(value = "quote_position")
        int quotePosition,

        @JsonProperty(value = "checklist_task_id")
        int checklistTaskId
) implements ApiResult {

    public static @NotNull Builder ofBuilder(int messageId) {
        return new ReplyParameters.Builder().messageId(messageId);
    }

}
