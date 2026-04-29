package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

import java.util.Date;

public record PollOption(
        @JsonProperty(value = "persistent_id", required = true)
        String persistentId,

        @JsonProperty(value = "text", required = true)
        String text,

        @JsonProperty(value = "text_entities")
        @Nullable
        MessageEntity[] textEntities,

        @JsonProperty(value = "voter_count", required = true)
        int voterCount,

        @JsonProperty(value = "added_by_user")
        @Nullable
        User addedByUser,

        @JsonProperty(value = "addition_date")
        @Nullable
        Date additionDate
) implements ApiResult {

}
