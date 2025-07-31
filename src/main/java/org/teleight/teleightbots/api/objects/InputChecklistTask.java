package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record InputChecklistTask(
        @JsonProperty(value = "id", required = true)
        int id,

        @JsonProperty(value = "text", required = true)
        String text,

        @JsonProperty(value = "parse_mode")
        @Nullable
        ParseMode parseMode,

        @JsonProperty(value = "text_entities")
        @Nullable
        MessageEntity[] textEntities
) implements ApiResult {

    public static @NotNull InputChecklistTask.Builder ofBuilder(int id, String text) {
        return new InputChecklistTask.Builder().id(id).text(text);
    }

}
