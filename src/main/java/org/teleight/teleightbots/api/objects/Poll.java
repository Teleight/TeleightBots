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

        @JsonProperty(value = "question_entities")
        @Nullable
        MessageEntity[] questionEntities,

        @JsonProperty(value = "options", required = true)
        PollOption[] options,

        @JsonProperty(value = "total_voter_count", required = true)
        int totalVoterCount,

        @JsonProperty(value = "is_closed", required = true)
        boolean isClosed,

        @JsonProperty(value = "is_anonymous", required = true)
        boolean isAnonymous,

        @JsonProperty(value = "type", required = true)
        PollType type,

        @JsonProperty(value = "allows_multiple_answers")
        boolean allowsMultipleAnswers,

        @JsonProperty(value = "allows_revoting")
        boolean allowsRevoting,

        @JsonProperty(value = "correct_option_ids")
        int[] correctOptionIds,

        @JsonProperty(value = "explanation")
        @Nullable
        String explanation,

        @JsonProperty(value = "explanation_entities")
        @Nullable
        MessageEntity[] explanationEntities,

        @JsonProperty(value = "open_period")
        int openPeriod,

        @JsonProperty(value = "close_date")
        @Nullable
        Date closeDate,

        @JsonProperty(value = "description")
        @Nullable
        String description,

        @JsonProperty(value = "description_entities")
        @Nullable
        MessageEntity[] descriptionEntities
) implements ApiResult {

}
