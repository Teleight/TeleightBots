package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
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
        InputChecklistTask[] tasks,

        @JsonProperty(value = "others_can_add_tasks")
        boolean othersCanAddTasks,

        @JsonProperty(value = "others_can_mark_tasks_as_done")
        boolean othersCanMarkTasksAsDone
) implements ApiResult {

    public static @NotNull InputChecklist.Builder ofBuilder(String title, InputChecklistTask[] tasks) {
        return new InputChecklist.Builder().title(title).tasks(tasks);
    }

}
