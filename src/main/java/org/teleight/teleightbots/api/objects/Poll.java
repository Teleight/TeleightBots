package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

import java.util.Date;

public record Poll(
        @JsonProperty(value = "id", required = true)
        String id,

        @JsonProperty(value = "question", required = true)
        String question,

        @JsonProperty(value = "options", required = true)
        PollOption[] options,

        @JsonProperty(value = "total_voter_count", required = true)
        int totalVoterCount,

        @JsonProperty(value = "is_closed", required = true)
        boolean isClosed,

        @JsonProperty(value = "is_anonymous", required = true)
        boolean isAnonymous,

        // regular or quiz. makes more sense to get an enum here
        @JsonProperty(value = "type", required = true)
        String type,

        @JsonProperty(value = "allows_multiple_answers", required = true)
        boolean allowsMultipleAnswers,

        @JsonProperty("correct_option_id")
        int correctOptionId,

        @JsonProperty("explanation")
        @Nullable
        String explanation,

        @JsonProperty("explanation_entities")
        @Nullable
        MessageEntity[] explanationEntities,

        @JsonProperty("open_period")
        int openPeriod,

        @JsonProperty("close_date")
        @Nullable
        Date closeDate
) implements ApiResult {

}
