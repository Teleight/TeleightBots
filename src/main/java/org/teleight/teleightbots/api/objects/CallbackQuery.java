package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record CallbackQuery(
        @JsonProperty(value = "id", required = true)
        String id,

        @JsonProperty(value = "from", required = true)
        User from,

        @JsonProperty("message")
        @Nullable
        MaybeInaccessibleMessage message,

        @JsonProperty("inline_message_id")
        @Nullable
        String inlineMessageId,

        @JsonProperty(value = "chat_instance", required = true)
        String chatInstance,

        @JsonProperty("data")
        @Nullable
        String data
) implements ApiResult {

    public static @NotNull Builder ofBuilder(String id, User from) {
        return new CallbackQuery.Builder().id(id).from(from);
    }

}
