package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record ChecklistTasksDone(
        @JsonProperty(value = "checklist_message")
        @Nullable
        Message checklistMessage,

        @JsonProperty(value = "marked_as_done_task_ids")
        int[] markedAsDoneTaskIds,

        @JsonProperty(value = "marked_as_not_done_task_ids")
        int[] markedAsNotDoneTaskIds
) implements ApiResult {
}
