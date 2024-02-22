package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.inline.result.InlineQueryResult;

@Builder
public record AnswerInlineQuery(
        @JsonProperty(value = "inline_query_id", required = true)
        @NotNull
        String inlineQueryId,

        @JsonProperty(value = "results", required = true)
        @NotNull
        InlineQueryResult[] results,

        @JsonProperty(value = "cache_time")
        @Nullable
        Integer cacheTime,

        @JsonProperty(value = "is_personal")
        @Nullable
        Boolean isPersonal
) implements ApiMethodBoolean {

    @Override
    public @NotNull String getEndpointURL() {
        return "answerInlineQuery";
    }

}
