package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

import java.util.Date;

public record ChecklistTask(
        @JsonProperty(value = "id", required = true)
        int id,

        @JsonProperty(value = "text", required = true)
        String text,

        @JsonProperty(value = "text_entities")
        @Nullable
        MessageEntity[] textEntities,

        @JsonProperty(value = "completed_by_user")
        @Nullable
        User completedByUser,

        @JsonProperty(value = "completed_by_chat")
        @Nullable
        Chat completedByChat,

        @JsonProperty(value = "completion_date")
        @Nullable
        Date completionDate
) implements ApiResult {
}
