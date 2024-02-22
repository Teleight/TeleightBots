package org.teleight.teleightbots.api.objects.poll;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.entities.MessageEntity;

import java.util.Date;

public record Poll(
        @JsonProperty(value = "id", required = true)
        String id,

        @JsonProperty(value = "question", required = true)
        String question,

        @JsonProperty(value = "options", required = true)
        PollOption[] options,

        @JsonProperty(value = "total_voter_count", required = true)
        Integer totalVoterCount,

        @JsonProperty(value = "is_closed", required = true)
        Boolean isClosed,

        @JsonProperty(value = "is_anonymous", required = true)
        Boolean isAnonymous,

        // regular or quiz. makes more sense to get an enum here
        @JsonProperty(value = "type", required = true)
        String type,

        @JsonProperty(value = "allows_multiple_answers", required = true)
        Boolean allowsMultipleAnswers,

        @JsonProperty("correct_option_id")
        @Nullable
        Integer correctOptionId,

        @JsonProperty("explanation")
        @Nullable
        String explanation,

        @JsonProperty("explanation_entities")
        @Nullable
        MessageEntity[] explanationEntities,

        @JsonProperty("open_period")
        @Nullable
        Integer openPeriod,

        @JsonProperty("close_date")
        @Nullable
        Date closeDate
) implements ApiResult {

}
