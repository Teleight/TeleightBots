package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

import java.util.List;

public record PollOptionAdded(
        @JsonProperty(value = "poll_message")
        @Nullable
        MaybeInaccessibleMessage pollMessage,

        @JsonProperty(value = "option_persistent_id", required = true)
        @NotNull
        String optionPersistentId,

        @JsonProperty(value = "option_text", required = true)
        @NotNull
        String optionText,

        @JsonProperty(value = "option_text_entities")
        @Nullable
        List<MessageEntity> optionTextEntities
) implements ApiResult {

}
