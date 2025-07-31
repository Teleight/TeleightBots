package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record ChecklistTasksAdded(
        @JsonProperty(value = "checklist_message")
        @Nullable
        Message checklistMessage,

        @JsonProperty(value = "tasks", required = true)
        ChecklistTask[] tasks
) implements ApiResult {
}
