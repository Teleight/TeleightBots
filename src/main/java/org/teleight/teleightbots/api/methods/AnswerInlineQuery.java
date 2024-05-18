package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.InlineQueryResult;

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

    public static AnswerInlineQuery.Builder ofBuilder(String inlineQueryId) {
        return new AnswerInlineQuery.Builder(inlineQueryId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "answerInlineQuery";
    }

    public static class Builder {
        private final String inlineQueryId;
        private InlineQueryResult[] results;
        private int cacheTime;
        private boolean isPersonal;

        Builder(String inlineQueryId) {
            this.inlineQueryId = inlineQueryId;
        }

        public Builder results(InlineQueryResult[] results) {
            this.results = results;
            return this;
        }

        public Builder cacheTime(int cacheTime) {
            this.cacheTime = cacheTime;
            return this;
        }

        public Builder isPersonal(boolean isPersonal) {
            this.isPersonal = isPersonal;
            return this;
        }

        public AnswerInlineQuery build() {
            return new AnswerInlineQuery(this.inlineQueryId, this.results, this.cacheTime, this.isPersonal);
        }
    }
}
