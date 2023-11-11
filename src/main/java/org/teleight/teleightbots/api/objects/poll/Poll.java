package org.teleight.teleightbots.api.objects.poll;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.entities.MessageEntity;

public record Poll(
        @JsonProperty("id")
        String id,

        @JsonProperty("question")
        String question,

        @JsonProperty("options")
        PollOption[] options,

        @JsonProperty("total_voter_count")
        int totalVoterCount,

        @JsonProperty("is_closed")
        boolean isClosed,

        @JsonProperty("is_anonymous")
        boolean isAnonymous,

        // regular or quiz. makes more sense to get an enum here
        @JsonProperty("type")
        String type,

        @JsonProperty("allows_multiple_answers")
        boolean allowsMultipleAnswers,

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
        Integer closeDate
) implements ApiResult {

}
