package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record InputChecklist(
        @JsonProperty(value = "title", required = true)
        String title,

        @JsonProperty(value = "parse_mode")
        @Nullable
        ParseMode parseMode,

        @JsonProperty(value = "title_entities")
        @Nullable
        MessageEntity[] titleEntities,

        @JsonProperty(value = "tasks", required = true)
        ChecklistTask[] tasks,

        @JsonProperty(value = "others_can_add_tasks")
        boolean othersCanAddTasks,

        @JsonProperty(value = "others_can_mark_tasks_as_done")
        boolean othersCanMarkTasksAsDone
) implements ApiResult {
}
