package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.InlineQueryResult;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record AnswerInlineQuery(
        @JsonProperty(value = "inline_query_id", required = true)
        @NotNull
        String inlineQueryId,

        @JsonProperty(value = "results", required = true)
        @NotNull
        InlineQueryResult[] results,

        @JsonProperty(value = "cache_time")
        int cacheTime,

        @JsonProperty(value = "is_personal")
        boolean isPersonal
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String inlineQueryId) {
        return new AnswerInlineQuery.Builder().inlineQueryId(inlineQueryId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "answerInlineQuery";
    }

}
